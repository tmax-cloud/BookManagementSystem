package org.tmaxcloud.sample.msa.book.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {
    private Long id;
    private float rating;

    public Rating() {
    }

    public Rating(Long id, float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format(
                "Rating[id=%d, rating='%f']",
                id, rating);
    }

    public Long getId() {
        return this.id;
    }

    public float getRating() {
        return this.rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
