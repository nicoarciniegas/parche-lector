package com.parchelector.repository;

import com.parchelector.model.entity.Follow;
import com.parchelector.model.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Follow entity.
 * 
 * @author Nicolas Arciniegas
 */
@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.followedId = :userId")
    long countFollowersByUserId(Long userId);

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.followerId = :userId")
    long countFollowingByUserId(Long userId);
}
