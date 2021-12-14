package org.tmaxcloud.sample.msa.book.rating;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByBookId(Long id);
    Rating findTopByBookIdOrderByCreatedTimeAtDesc(Long bookId);
}
