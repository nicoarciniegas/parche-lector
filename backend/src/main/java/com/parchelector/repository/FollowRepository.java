package com.parchelector.repository;

import com.parchelector.model.entity.Follow;
import com.parchelector.model.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);

    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);

    Optional<Follow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);

    @Query("SELECT f.followedId FROM Follow f WHERE f.followerId = :userId")
    List<Long> findFollowedUserIds(Long userId);
}
