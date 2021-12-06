package org.tmaxcloud.sample.msa.book.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderRepository repository;
    private final RestTemplate restTemplate;
    private final KafkaProducer producer;

    @Value("${BOOK_PAYMENT_URL}")
    private String paymentSvcAddr;

    public OrderController(OrderRepository repository, RestTemplate restTemplate, KafkaProducer producer) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.producer = producer;
    }

    @GetMapping("/orders")
    public List<Order> all() {
        return repository.findAll();
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> newOrder(@RequestBody Order order) {
        HttpEntity<Payment> payment = new HttpEntity<>(new Payment(order.getId()));
        ResponseEntity<Payment> responseEntity = restTemplate.postForEntity(
                paymentSvcAddr + "/payment", payment, Payment.class);

        if (HttpStatus.OK != responseEntity.getStatusCode()) {
            log.info("failed to call payment for new order...");
            return ResponseEntity.internalServerError().build();
        }

        repository.save(order);
        Payment response = responseEntity.getBody();
        order.setPaymentId(response.getId());

        return ResponseEntity.ok(order);
    }

    @PostMapping("/orders/{id}")
    public Order replaceOrder(@RequestBody Order newOrder, @PathVariable Long id) {
        return repository.findById(id)
                .map(order -> {
                    order.setType(newOrder.getType());
                    order.setQuantity(newOrder.getQuantity());
                    order.setBookId(newOrder.getBookId());
                    return repository.save(order);
                })
                .orElseGet(() -> {
                    newOrder.setId(id);
                    return repository.save(newOrder);
                });
    }

    @PutMapping("/orders/{id}/success")
    public String successOrder(@RequestBody Payment payment, @PathVariable Long id) {
        String ret = "success";

        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (payment.getId() != order.getPaymentId()) {
            log.warn("does not match order's payment id");
            ret = "failed";
        }

        switch (order.getType()) {
            case PURCHASE:
                producer.sendPurchaseMessage(order.getQuantity());
                break;
            case SALE:
                producer.sendSaleMessage(order.getQuantity());
                break;
            case RENT:
                producer.sendRentMessage(order.getQuantity());
                break;
            default:
                ret = "failed";
        }

        return ret;
    }
}