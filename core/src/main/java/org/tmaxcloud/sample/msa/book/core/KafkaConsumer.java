package org.tmaxcloud.sample.msa.book.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    private final BookRepository repository;

    KafkaConsumer(BookRepository repository){
        this.repository = repository;
    }

    @KafkaListener(topics = "purchase")
    public void consumePurchaseMessage(String quantity) {
        log.info(String.format("Consume purchase message : %s", quantity));
        Long id = 1L;
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        log.info("{} quantity++", book);
    }

    @KafkaListener(topics = "sale")
    public void consumeSaleMessage(String quantity) {
        log.info(String.format("Consume sale message : %s", quantity));
        Long id = 1L;
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        log.info("{} quantity++", book);
    }

    @KafkaListener(topics = "rent")
    public void consumeRentMessage(String quantity) {
        log.info(String.format("Consume rent message : %s", quantity));
        Long id = 1L;
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        log.info("{} quantity++", book);
    }
}
