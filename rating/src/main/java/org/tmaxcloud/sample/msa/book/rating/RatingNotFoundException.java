package org.tmaxcloud.sample.msa.book.rating;

public class RatingNotFoundException extends RuntimeException {
    RatingNotFoundException(Long id) {
        super("Could not find book rating " + id);
    }
}
