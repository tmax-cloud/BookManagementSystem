package orgltmaxcloud.sample.msa.book.common.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {
    private Long id;
    private Long bookId;
    private float score;

    public Rating() {}

    public Rating(Long bookId, float score) {
        this.bookId = bookId;
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format(
                "Rating[id=%d, score='%f']",
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

    public void setScore(float score) {
        this.score = score;
    }
}