package com.parchelector.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity for favorite_books (many-to-many relationship).
 * 
 * @author Nicolas Arciniegas
 */
@Entity
@Table(name = "favorite_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteBook {

    @EmbeddedId
    private FavoriteBookId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

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
    public static class FavoriteBookId implements Serializable {
        @Column(name = "user_id")
        private Long userId;

        @Column(name = "book_id")
        private Long bookId;
    }
}
