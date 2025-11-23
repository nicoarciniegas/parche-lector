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

    // Filter and sort by popularity (number of reviews + reading statuses)
    @Query("SELECT b FROM Book b " +
           "LEFT JOIN b.genres g " +
           "WHERE (:genre IS NULL OR g.name = :genre) " +
           "AND (:minYear IS NULL OR b.publishedYear >= :minYear) " +
           "AND (:maxYear IS NULL OR b.publishedYear <= :maxYear) " +
           "ORDER BY (" +
           "  (SELECT COUNT(r) FROM Review r WHERE r.book.id = b.id AND r.isDeleted = false) + " +
           "  (SELECT COUNT(rs) FROM ReadingStatus rs WHERE rs.book.id = b.id)" +
           ") DESC")
    List<Book> findBooksFilteredAndSortedByPopularity(String genre, Integer minYear, Integer maxYear, Pageable pageable);

    // Filter and sort by average rating
    @Query("SELECT b FROM Book b " +
           "LEFT JOIN b.genres g " +
           "WHERE (:genre IS NULL OR g.name = :genre) " +
           "AND (:minYear IS NULL OR b.publishedYear >= :minYear) " +
           "AND (:maxYear IS NULL OR b.publishedYear <= :maxYear) " +
           "ORDER BY (" +
           "  SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.book.id = b.id AND r.isDeleted = false" +
           ") DESC")
    List<Book> findBooksFilteredAndSortedByRating(String genre, Integer minYear, Integer maxYear, Pageable pageable);

    // Filter and sort by newest (publication year descending)
    @Query("SELECT b FROM Book b " +
           "LEFT JOIN b.genres g " +
           "WHERE (:genre IS NULL OR g.name = :genre) " +
           "AND (:minYear IS NULL OR b.publishedYear >= :minYear) " +
           "AND (:maxYear IS NULL OR b.publishedYear <= :maxYear) " +
           "ORDER BY b.publishedYear DESC NULLS LAST")
    List<Book> findBooksFilteredAndSortedByNewest(String genre, Integer minYear, Integer maxYear, Pageable pageable);

    // Filter and sort by oldest (publication year ascending)
    @Query("SELECT b FROM Book b " +
           "LEFT JOIN b.genres g " +
           "WHERE (:genre IS NULL OR g.name = :genre) " +
           "AND (:minYear IS NULL OR b.publishedYear >= :minYear) " +
           "AND (:maxYear IS NULL OR b.publishedYear <= :maxYear) " +
           "ORDER BY b.publishedYear ASC NULLS LAST")
    List<Book> findBooksFilteredAndSortedByOldest(String genre, Integer minYear, Integer maxYear, Pageable pageable);
}
