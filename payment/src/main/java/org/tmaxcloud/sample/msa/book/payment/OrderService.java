package org.tmaxcloud.sample.msa.book.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final RestTemplate restTemplate;

    @Value("${BOOK_ORDER_URL}")
    private String orderSvcAddr;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public void payFuture(Payment payment) {
        try {
            int second = (int) (Math.random() * 10) + 1;
            Thread.sleep(second * 1000L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        log.info("process paying {}", payment);
        ResponseEntity<String> response = restTemplate.postForEntity(
                orderSvcAddr + "/orders/{id}/process", payment.getOrderId(), String.class, payment.getOrderId());

        if (response.getStatusCode() != HttpStatus.OK) {
            log.warn("failed to process paying: {}", payment);
            return;
        }

        log.info("order({}) paid {}", payment.getOrderId(), response.getBody());
    }
}
