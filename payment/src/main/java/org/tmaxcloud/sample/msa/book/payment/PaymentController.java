package org.tmaxcloud.sample.msa.book.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tmaxcloud.sample.msa.book.common.dto.OrderDto;
import org.tmaxcloud.sample.msa.book.common.dto.PaymentDto;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final OrderService orderService;

    public PaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/payments")
    public PaymentDto newPayment(@RequestBody OrderDto orderDto) {
        PaymentDto paymentDto = new PaymentDto()
                .setId(new Random().nextLong())
                .setOrderId(orderDto.getId());

        log.info("issued payment: {}", paymentDto);
        orderService.payFuture(paymentDto);
        return paymentDto;
    }
}