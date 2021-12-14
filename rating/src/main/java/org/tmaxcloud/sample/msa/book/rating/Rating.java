package org.tmaxcloud.sample.msa.book.rating;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "BOOK_RATING")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private float score;
    @CreationTimestamp
    private LocalDateTime createdTimeAt;

    public Rating(float score) {
        this.score = score;
    }

    public Rating(Long bookId, float score) {
        this.bookId = bookId;
        this.score = score;
    }

    public Rating(Long bookId, float score, LocalDateTime created) {
        this.bookId = bookId;
        this.score = score;
        this.createdTimeAt = created;
    }
}
