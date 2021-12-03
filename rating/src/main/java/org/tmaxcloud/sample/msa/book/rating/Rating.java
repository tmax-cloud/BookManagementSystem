package org.tmaxcloud.sample.msa.book.rating;


import javax.persistence.*;

@Entity
@Table(name = "BOOK_RATING")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float rating;

    public Rating() {
    }

    public Rating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format(
                "Rating[id=%d, rating='%s']",
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
