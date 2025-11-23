package com.parchelector.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity for list_likes.
 * 
 * @author Nicolas Arciniegas
 */
@Entity
@Table(name = "list_likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListLike {

    @EmbeddedId
    private ListLikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("listId")
    @JoinColumn(name = "list_id")
    private LibraryList list;

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
    public static class ListLikeId implements Serializable {
        @Column(name = "list_id")
        private Long listId;

        @Column(name = "user_id")
        private Long userId;
    }
}
