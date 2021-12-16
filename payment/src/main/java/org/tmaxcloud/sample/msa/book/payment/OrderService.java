package org.tmaxcloud.sample.msa.book.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tmaxcloud.sample.msa.book.common.dto.PaymentDto;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final RestTemplate restTemplate;

    @Value("${upstream.order}")
    private String orderServiceUrl;

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

        PaymentDto paymentDto = new PaymentDto()
                .setId(payment.getId())
                .setOrderId(payment.getOrderId());

        ResponseEntity<String> response = restTemplate.postForEntity(
                orderServiceUrl + "/api/orders/{id}/process", paymentDto, String.class, payment.getOrderId());
        if (response.getStatusCode() != HttpStatus.OK) {
            log.warn("failed to process paying: {}", paymentDto);
            return;
        }

        log.info("order({}) paid {}", paymentDto, response.getBody());
    }
}
