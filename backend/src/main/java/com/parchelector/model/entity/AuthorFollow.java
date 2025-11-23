package com.parchelector.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * AuthorFollow entity representing user-author follow relationships.
 * 
 * @author Nicolas Arciniegas
 */
@Entity
@Table(name = "author_follows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AuthorFollowId.class)
public class AuthorFollow {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "author_id")
    private Long authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private Author author;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
