package com.parchelector.repository;

import com.parchelector.model.entity.ListBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for ListBook entity.
 * 
 * @author Nicolas Arciniegas
 */
@Repository
public interface ListBookRepository extends JpaRepository<ListBook, ListBook.ListBookId> {
    
    @Query("SELECT lb FROM ListBook lb JOIN FETCH lb.book b LEFT JOIN FETCH b.authors WHERE lb.list.id = :listId ORDER BY lb.position")
    List<ListBook> findByListIdWithBooks(Long listId);
}
