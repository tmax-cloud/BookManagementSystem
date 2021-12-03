package org.tmaxcloud.sample.msa.book.rating;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RatingRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Rating((float) 3.9)));
            log.info("Preloading " + repository.save(new Rating((float) 4.4)));
            log.info("Preloading " + repository.save(new Rating((float) 3.4)));
            log.info("Preloading " + repository.save(new Rating((float) 2.1)));
        };
    }
}