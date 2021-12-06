package org.tmaxcloud.sample.msa.book.rating;


import javax.persistence.*;

@Entity
@Table(name = "BOOK_RATING")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private float score;

    public Rating() {}

    public Rating(float score) {
        this.score = score;
    }

    public Rating(Long bookId, float score) {
        this.bookId = bookId;
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format(
                "Rating[id=%d, rating='%s']",
                id, score);
    }

    public Long getId() {
        return this.id;
    }

    public Long getBookId() {
        return this.bookId;
    }

    public float getScore() {
        return this.score;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookId(Long id) {
        this.bookId = id;
    }

    public void setScore(float rating) {
        this.score = rating;
    }
}
