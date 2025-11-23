package com.parchelector.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity for list_books (many-to-many relationship).
 * 
 * @author Nicolas Arciniegas
 */
@Entity
@Table(name = "list_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListBook {

    @EmbeddedId
    private ListBookId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("listId")
    @JoinColumn(name = "list_id")
    private LibraryList list;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    private Integer position = 1;

    @Column(length = 255)
    private String note;

    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListBookId implements Serializable {
        @Column(name = "list_id")
        private Long listId;

        @Column(name = "book_id")
        private Long bookId;
    }
}
