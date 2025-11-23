package com.parchelector.repository;

import com.parchelector.model.entity.AuthorFollow;
import com.parchelector.model.entity.AuthorFollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for AuthorFollow entity.
 * 
 * @author Nicolas Arciniegas
 */
@Repository
public interface AuthorFollowRepository extends JpaRepository<AuthorFollow, AuthorFollowId> {

    @Query("SELECT COUNT(af) FROM AuthorFollow af WHERE af.authorId = :authorId")
    long countFollowersByAuthorId(Long authorId);

    @Query("SELECT COUNT(af) FROM AuthorFollow af WHERE af.userId = :userId")
    long countAuthorFollowsByUserId(Long userId);

    boolean existsByUserIdAndAuthorId(Long userId, Long authorId);

    void deleteByUserIdAndAuthorId(Long userId, Long authorId);

    Optional<AuthorFollow> findByUserIdAndAuthorId(Long userId, Long authorId);
}
