package com.parchelector.repository;

import com.parchelector.model.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Book entity.
 * 
 * @author Nicolas Arciniegas
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title);

    Optional<Book> findByIsbn13(String isbn13);

    List<Book> findByPublishedYear(Integer year);

    @Query("SELECT DISTINCT b FROM Book b " +
           "LEFT JOIN FETCH b.authors a " +
           "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> searchByTitleOrAuthor(String query, Pageable pageable);
}
