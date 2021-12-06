package org.tmaxcloud.sample.msa.book.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentRepository repository;
    private final OrderService orderService;

    public PaymentController(PaymentRepository repository, OrderService orderService) {
        this.repository = repository;
        this.orderService = orderService;
    }

    @GetMapping("/payments")
    public List<Payment> all() {
        return repository.findAll();
    }

    @PostMapping("/payments")
    public Payment newPayment(@RequestBody Payment payment) {
        log.info("new payment: {}", payment);
        Payment issuedPayment = this.repository.save(payment);

        log.info("issued payment: {}", issuedPayment);
        orderService.payFuture(issuedPayment);
        return issuedPayment;
    }
}