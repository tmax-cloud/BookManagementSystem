package org.tmaxcloud.sample.msa.book.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Value("${BOOK_ORDER_URL}")
    private String orderSvcAddr;

    private final PaymentRepository repository;
    private final RestTemplate restTemplate;

    public PaymentController(PaymentRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<Payment> newPayment(@RequestBody Order order) {
        try {
            int second = (int) (Math.random() * 10) + 1;
            Thread.sleep(second * 1000L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        Payment payment = new Payment(order.getId());
        payment = this.repository.save(payment);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                orderSvcAddr + "/order/{id}/success", payment, String.class, order.getId());

        String response = responseEntity.getBody();
        if ("failed".compareTo(Objects.requireNonNull(response)) == 0) {
            log.warn("failed to call order for payment");
            return ResponseEntity.internalServerError().body(payment);
        }

        return ResponseEntity.ok(payment);
    }
}