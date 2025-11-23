package com.parchelector.repository;

import com.parchelector.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Review entity.
 * 
 * @author Nicolas Arciniegas
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.book.id = :bookId AND r.isDeleted = false")
    Optional<Review> findByUserIdAndBookId(Long userId, Long bookId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.book.id = :bookId AND r.isDeleted = false")
    Double getAverageRatingByBookId(Long bookId);
}
