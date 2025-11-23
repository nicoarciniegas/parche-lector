package com.parchelector.repository;

import com.parchelector.model.entity.ListLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for ListLike entity.
 * 
 * @author Nicolas Arciniegas
 */
@Repository
public interface ListLikeRepository extends JpaRepository<ListLike, ListLike.ListLikeId> {
    
    @Query("SELECT COUNT(ll) FROM ListLike ll WHERE ll.list.id = :listId")
    int countByListId(Long listId);
}
