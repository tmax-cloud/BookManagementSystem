package org.tmaxcloud.sample.msa.book.rating;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RatingController {
    private final RatingRepository repository;

    RatingController(RatingRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/rating")
    List<Rating> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/rating")
    Rating newRating(@RequestBody Rating newRating) {
        return repository.save(newRating);
    }

    // Single item

    @GetMapping("/rating/{id}")
    Rating one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(id));
    }

    @PutMapping("/rating/{id}")
    Rating replaceRating(@RequestBody Rating newRating, @PathVariable Long id) {

        return repository.findById(id)
                .map(rating -> {
                    rating.setRating(newRating.getRating());
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
