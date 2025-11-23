package com.parchelector.service;

import com.parchelector.dto.request.FollowAuthorRequest;
import com.parchelector.dto.request.FollowUserRequest;
import com.parchelector.dto.response.FollowResponse;
import com.parchelector.dto.response.UserFollowStatsResponse;
import com.parchelector.model.entity.*;
import com.parchelector.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for social features (follows, etc.).
 * 
 * @author Nicolas Arciniegas
 */
@Service
public class SocialService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private AuthorFollowRepository authorFollowRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Follow a user.
     */
    @Transactional
    public FollowResponse followUser(Long followerId, FollowUserRequest request) {
        Long followedId = request.getUserId();

        // Validate users exist
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower user not found"));
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new IllegalArgumentException("User to follow not found"));

        // Cannot follow yourself
        if (followerId.equals(followedId)) {
            throw new IllegalArgumentException("You cannot follow yourself");
        }

        // Check if already following
        if (followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            throw new IllegalArgumentException("You are already following this user");
        }

        // Create follow relationship
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFollowedId(followedId);
        follow = followRepository.save(follow);

        return new FollowResponse(
                followerId,
                follower.getUsername(),
                followedId,
                followed.getUsername(),
                follow.getCreatedAt()
        );
    }

    /**
     * Unfollow a user.
     */
    @Transactional
    public void unfollowUser(Long followerId, Long followedId) {
        // Validate relationship exists
        if (!followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            throw new IllegalArgumentException("You are not following this user");
        }

        followRepository.deleteByFollowerIdAndFollowedId(followerId, followedId);
    }

    /**
     * Follow an author.
     */
    @Transactional
    public void followAuthor(Long userId, FollowAuthorRequest request) {
        Long authorId = request.getAuthorId();

        // Validate user and author exist
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        // Check if already following
        if (authorFollowRepository.existsByUserIdAndAuthorId(userId, authorId)) {
            throw new IllegalArgumentException("You are already following this author");
        }

        // Create author follow relationship
        AuthorFollow authorFollow = new AuthorFollow();
        authorFollow.setUserId(userId);
        authorFollow.setAuthorId(authorId);
        authorFollowRepository.save(authorFollow);
    }

    /**
     * Unfollow an author.
     */
    @Transactional
    public void unfollowAuthor(Long userId, Long authorId) {
        // Validate relationship exists
        if (!authorFollowRepository.existsByUserIdAndAuthorId(userId, authorId)) {
            throw new IllegalArgumentException("You are not following this author");
        }

        authorFollowRepository.deleteByUserIdAndAuthorId(userId, authorId);
    }

    /**
     * Get user follow statistics.
     */
    public UserFollowStatsResponse getUserFollowStats(Long userId, Long currentUserId) {
        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        long followersCount = followRepository.countFollowersByUserId(userId);
        long followingCount = followRepository.countFollowingByUserId(userId);
        
        // Check if current user is following this user
        Boolean isFollowing = null;
        if (currentUserId != null && !currentUserId.equals(userId)) {
            isFollowing = followRepository.existsByFollowerIdAndFollowedId(currentUserId, userId);
        }

        return new UserFollowStatsResponse(
                userId,
                user.getUsername(),
                followersCount,
                followingCount,
                isFollowing
        );
    }

    /**
     * Check if a user is following another user.
     */
    public boolean isFollowing(Long followerId, Long followedId) {
        return followRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
    }

    /**
     * Check if a user is following an author.
     */
    public boolean isFollowingAuthor(Long userId, Long authorId) {
        return authorFollowRepository.existsByUserIdAndAuthorId(userId, authorId);
    }
}
