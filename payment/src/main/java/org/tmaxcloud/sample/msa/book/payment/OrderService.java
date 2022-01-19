package org.tmaxcloud.sample.msa.book.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.tmaxcloud.sample.msa.book.common.dto.PaymentDto;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final WebClient webClient;

    @Value("${upstream.order}")
    private String orderServiceUrl;

    public OrderService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Async
    public void payFuture(PaymentDto paymentDto) {
        try {
            int second = (int) (Math.random() * 10) + 4;
            Thread.sleep(second * 1000L);

            Mono<String> response = webClient.post()
                    .uri(orderServiceUrl + "/api/orders/" + paymentDto.getOrderId() + "/process")
                    .bodyValue(paymentDto)
                    .retrieve()
                    .bodyToMono(String.class);

            response.subscribe(res -> {
                log.info("order({}) paid {}", paymentDto, res);
            }, e -> {
                log.warn("failed to process paying: {} {}", paymentDto, e);
            });

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return;
    }
}
