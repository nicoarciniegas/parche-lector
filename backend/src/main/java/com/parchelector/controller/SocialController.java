package com.parchelector.controller;

import com.parchelector.dto.ApiResponse;
import com.parchelector.dto.request.FollowAuthorRequest;
import com.parchelector.dto.request.FollowUserRequest;
import com.parchelector.dto.response.FeedResponse;
import com.parchelector.dto.response.FollowResponse;
import com.parchelector.dto.response.UserFollowStatsResponse;
import com.parchelector.model.entity.User;
import com.parchelector.repository.UserRepository;
import com.parchelector.service.SocialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for social features (follows, etc.).
 * 
 * @author Nicolas Arciniegas
 */
@RestController
@RequestMapping("/social")
@Tag(name = "Social", description = "Endpoints for social features (follow/unfollow)")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Follow a user.
     */
    @PostMapping("/follow/user")
    @Operation(summary = "Follow a user", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<FollowResponse>> followUser(@Valid @RequestBody FollowUserRequest request) {
        try {
            Long currentUserId = getCurrentUserId();
            FollowResponse response = socialService.followUser(currentUserId, request);
            
            ApiResponse<FollowResponse> apiResponse = new ApiResponse<>(
                    "SUCCESS",
                    "User followed successfully",
                    response
            );
            return ResponseEntity.ok(apiResponse);
        } catch (IllegalArgumentException e) {
            ApiResponse<FollowResponse> apiResponse = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<FollowResponse> apiResponse = new ApiResponse<>(
                    "ERROR",
                    "Failed to follow user: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Unfollow a user.
     */
    @DeleteMapping("/follow/user/{userId}")
    @Operation(summary = "Unfollow a user", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<Void>> unfollowUser(@PathVariable Long userId) {
        try {
            Long currentUserId = getCurrentUserId();
            socialService.unfollowUser(currentUserId, userId);
            
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    "SUCCESS",
                    "User unfollowed successfully",
                    null
            );
            return ResponseEntity.ok(apiResponse);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    "ERROR",
                    "Failed to unfollow user: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Follow an author.
     */
    @PostMapping("/follow/author")
    @Operation(summary = "Follow an author", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<Void>> followAuthor(@Valid @RequestBody FollowAuthorRequest request) {
        try {
            Long currentUserId = getCurrentUserId();
            socialService.followAuthor(currentUserId, request);
            
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    "SUCCESS",
                    "Author followed successfully",
                    null
            );
            return ResponseEntity.ok(apiResponse);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    "ERROR",
                    "Failed to follow author: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Unfollow an author.
     */
    @DeleteMapping("/follow/author/{authorId}")
    @Operation(summary = "Unfollow an author", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<Void>> unfollowAuthor(@PathVariable Long authorId) {
        try {
            Long currentUserId = getCurrentUserId();
            socialService.unfollowAuthor(currentUserId, authorId);
            
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    "SUCCESS",
                    "Author unfollowed successfully",
                    null
            );
            return ResponseEntity.ok(apiResponse);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    "ERROR",
                    "Failed to unfollow author: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Get user follow statistics.
     */
    @GetMapping("/users/{userId}/stats")
    @Operation(summary = "Get user follow statistics", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<UserFollowStatsResponse>> getUserFollowStats(@PathVariable Long userId) {
        try {
            Long currentUserId = getCurrentUserId();
            UserFollowStatsResponse stats = socialService.getUserFollowStats(userId, currentUserId);
            
            ApiResponse<UserFollowStatsResponse> apiResponse = new ApiResponse<>(
                    "SUCCESS",
                    "User follow statistics retrieved successfully",
                    stats
            );
            return ResponseEntity.ok(apiResponse);
        } catch (IllegalArgumentException e) {
            ApiResponse<UserFollowStatsResponse> apiResponse = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<UserFollowStatsResponse> apiResponse = new ApiResponse<>(
                    "ERROR",
                    "Failed to retrieve user statistics: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Check if current user is following another user.
     */
    @GetMapping("/follow/user/{userId}/status")
    @Operation(summary = "Check if following a user", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<Boolean>> isFollowingUser(@PathVariable Long userId) {
        try {
            Long currentUserId = getCurrentUserId();
            boolean isFollowing = socialService.isFollowing(currentUserId, userId);
            
            ApiResponse<Boolean> apiResponse = new ApiResponse<>(
                    "SUCCESS",
                    "Follow status retrieved successfully",
                    isFollowing
            );
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<Boolean> apiResponse = new ApiResponse<>(
                    "ERROR",
                    "Failed to check follow status: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Check if current user is following an author.
     */
    @GetMapping("/follow/author/{authorId}/status")
    @Operation(summary = "Check if following an author", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<Boolean>> isFollowingAuthor(@PathVariable Long authorId) {
        try {
            Long currentUserId = getCurrentUserId();
            boolean isFollowing = socialService.isFollowingAuthor(currentUserId, authorId);
            
            ApiResponse<Boolean> apiResponse = new ApiResponse<>(
                    "SUCCESS",
                    "Follow status retrieved successfully",
                    isFollowing
            );
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<Boolean> apiResponse = new ApiResponse<>(
                    "ERROR",
                    "Failed to check follow status: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Get social feed with activity from followed users.
     */
    @GetMapping("/feed")
    @Operation(summary = "Get social feed from followed users", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<FeedResponse>> getFeed(
            @Parameter(description = "Number of items to return") @RequestParam(defaultValue = "20") Integer limit,
            @Parameter(description = "Number of items to skip") @RequestParam(defaultValue = "0") Integer offset) {
        try {
            Long currentUserId = getCurrentUserId();
            FeedResponse feed = socialService.getFeed(currentUserId, limit, offset);
            
            ApiResponse<FeedResponse> apiResponse = new ApiResponse<>(
                    "SUCCESS",
                    "Feed retrieved successfully",
                    feed
            );
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<FeedResponse> apiResponse = new ApiResponse<>(
                    "ERROR",
                    "Failed to retrieve feed: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Get current authenticated user ID.
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getId();
    }
}
