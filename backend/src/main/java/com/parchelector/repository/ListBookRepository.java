package com.parchelector.repository;

import com.parchelector.model.entity.ListBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for ListBook entity.
 * 
 * @author Nicolas Arciniegas
 */
@Repository
public interface ListBookRepository extends JpaRepository<ListBook, ListBook.ListBookId> {
}
