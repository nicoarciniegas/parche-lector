package com.parchelector.repository;

import com.parchelector.model.entity.FavoriteBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for FavoriteBook entity.
 * 
 * @author Nicolas Arciniegas
 */
@Repository
public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, FavoriteBook.FavoriteBookId> {
    
    @Query("SELECT fb FROM FavoriteBook fb JOIN FETCH fb.book b LEFT JOIN FETCH b.authors WHERE fb.user.id = :userId")
    List<FavoriteBook> findByUserIdWithBooks(Long userId);
    
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
    
    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
