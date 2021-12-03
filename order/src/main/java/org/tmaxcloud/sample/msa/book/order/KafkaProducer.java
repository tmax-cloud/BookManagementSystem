package org.tmaxcloud.sample.msa.book.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private static final String TOPIC_PURCHASE = "purchase";
    private static final String TOPIC_SALE = "sale";
    private static final String TOPIC_RENT = "rent";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPurchaseMessage(int quantity) {
        log.info(String.format("Produce purchase message : %s", quantity));
        this.kafkaTemplate.send(TOPIC_PURCHASE, Integer.toString(quantity));

    }

    public void sendSaleMessage(int quantity) {
        log.info(String.format("Produce sale message : %s", quantity));
        this.kafkaTemplate.send(TOPIC_SALE, Integer.toString(quantity));
    }

    public void sendRentMessage(int quantity) {
        log.info(String.format("Produce rent message : %s", quantity));
        this.kafkaTemplate.send(TOPIC_RENT, Integer.toString(quantity));
    }
}
