package org.tmaxcloud.sample.msa.book.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "purchase", groupId = "book")
    public void consumePurchaseMessage(String quantity) {
        log.info(String.format("Consume purchase message : %s", quantity));
    }

    @KafkaListener(topics = "sale", groupId = "book")
    public void consumeSaleMessage(String quantity) {
        log.info(String.format("Consume sale message : %s", quantity));
    }

    @KafkaListener(topics = "rent", groupId = "book")
    public void consumeRentMessage(String quantity) {
        log.info(String.format("Consume rent message : %s", quantity));
    }
}
