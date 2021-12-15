package org.tmaxcloud.sample.msa.book.order;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.tmaxcloud.sample.msa.book.common.dto.OrderDto;
import org.tmaxcloud.sample.msa.book.common.dto.PaymentDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderRepository repository;

    private final OrderPaymentRepository paymentRepository;

    private final OrderPaymentService orderPaymentService;

    private final KafkaBookMessageProducer producer;

    private final ModelMapper modelMapper;

    public OrderController(OrderRepository repository, OrderPaymentRepository paymentRepository,
                           OrderPaymentService orderPaymentService,
                           KafkaBookMessageProducer producer, ModelMapper modelMapper) {
        this.repository = repository;
        this.paymentRepository = paymentRepository;
        this.orderPaymentService = orderPaymentService;
        this.producer = producer;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/orders")
    public List<OrderDto> all() {
        List<Order> orders = repository.findAll();
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/orders")
    public OrderDto newOrder(@RequestBody OrderDto orderDto) {
        Order savedOrder = repository.save(convertToEntity(orderDto));
        orderPaymentService.issuePaymentID(savedOrder);
        return convertToDto(savedOrder);
    }

    @PostMapping("/orders/{id}")
    public OrderDto replaceOrder(@RequestBody OrderDto newOrderDto, @PathVariable Long id) {
        return repository.findById(id)
                .map(order -> convertToDto(repository.save(order))
                )
                .orElseGet(() -> {
                    newOrderDto.setId(id);
                    return convertToDto(repository.save(convertToEntity(newOrderDto)));
                });
    }

    @PostMapping("/orders/{id}/process")
    public String processOrder(@RequestBody PaymentDto payment, @PathVariable Long id) {

        // TODO: check payment id
//        OrderPayment orderPayment = paymentRepository.findByOrderId(id)
//                .orElseThrow(() -> new OrderNotFoundException(id));
//
//        log.info("internal order payment info: {}", orderPayment);
//        log.info("request body payment info: {}", payment);
//
//        if (!Objects.equals(payment.getId(), orderPayment.getPaymentId())) {
//            log.warn("internal order payment id({}) does not match with responses {})",
//                    orderPayment.getPaymentId(), payment.getId());
//            return "failed (doesn't match payment id)";
//        }

        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        switch (order.getType()) {
            case PURCHASE:
                producer.sendPurchaseMessage(new BookMessage(order.getBookId(), order.getQuantity()));
                break;
            case SALE:
                producer.sendSaleMessage(new BookMessage(order.getBookId(), order.getQuantity()));
                break;
            case RENT:
                producer.sendRentMessage(new BookMessage(order.getBookId(), order.getQuantity()));
                break;
            default:
                return "failed (unknown order type)";
        }

        return "success";
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    private Order convertToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

}