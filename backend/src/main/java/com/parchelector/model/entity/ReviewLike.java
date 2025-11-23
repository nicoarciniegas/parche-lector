package com.parchelector.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity for review_likes.
 * 
 * @author Nicolas Arciniegas
 */
@Entity
@Table(name = "review_likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewLike {

    @EmbeddedId
    private ReviewLikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewId")
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewLikeId implements Serializable {
        @Column(name = "review_id")
        private Long reviewId;

        @Column(name = "user_id")
        private Long userId;
    }
}
