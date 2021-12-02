package org.tmaxcloud.sample.msa.book.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {
    private Long id;
    private Long bookId;
    private float rating;

    public Rating() {
    }

    public Rating(Long bookId, float rating) {
        this.bookId = bookId;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format(
                "Rating[id=%d, bookId='%d' rating='%s']",
                id, bookId, rating);
    }

    public Long getId() {
        return this.id;
    }

    public Long getBookId() {
        return this.bookId;
    }

    public float getRating() {
        return this.rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookId(Long id) {
        this.bookId = id;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}