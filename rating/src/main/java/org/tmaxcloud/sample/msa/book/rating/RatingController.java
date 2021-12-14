package org.tmaxcloud.sample.msa.book.rating;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RatingController {
    private static final Logger log = LoggerFactory.getLogger(RatingController.class);

    private final RatingRepository repository;

    RatingController(RatingRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/rating")
    List<Rating> all() {
        return repository.findAll();
    }

    @PostMapping("/rating")
    Rating newRating(@RequestBody Rating newRating) {
        return repository.save(newRating);
    }

    @GetMapping("/rating/{id}")
    Rating one(@PathVariable Long id) {
        return repository.findTopByBookIdOrderByCreatedTimeAtDesc(id);
    }

    @PutMapping("/rating/{id}")
    Rating replaceRating(@RequestBody Rating newRating, @PathVariable Long id) {
        return repository.findById(id)
                .map(rating -> {
                    rating.setBookId(newRating.getBookId());
                    rating.setScore(newRating.getScore());
                    return repository.save(rating);
                })
                .orElseGet(() -> {
                    newRating.setId(id);
                    return repository.save(newRating);
                });
    }

    @DeleteMapping("/rating/{id}")
    void deleteRating(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
