package com.parchelector.repository;

import com.parchelector.model.entity.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ReadingStatus entity.
 * 
 * @author Nicolas Arciniegas
 */
@Repository
public interface ReadingStatusRepository extends JpaRepository<ReadingStatus, Long> {

    @Query("SELECT rs FROM ReadingStatus rs " +
           "JOIN FETCH rs.book b " +
           "LEFT JOIN FETCH b.authors " +
           "WHERE rs.user.id = :userId " +
           "ORDER BY rs.updatedAt DESC")
    List<ReadingStatus> findByUserIdWithBooks(Long userId);

    @Query("SELECT rs FROM ReadingStatus rs WHERE rs.user.id = :userId AND rs.book.id = :bookId")
    Optional<ReadingStatus> findByUserIdAndBookId(Long userId, Long bookId);
    
    @Query("SELECT COUNT(rs) FROM ReadingStatus rs WHERE rs.user.id = :userId AND rs.status = :status")
    int countByUserIdAndStatus(Long userId, ReadingStatus.ReadingStatusEnum status);
}
