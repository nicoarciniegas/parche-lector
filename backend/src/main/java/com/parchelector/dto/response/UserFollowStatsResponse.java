package com.parchelector.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for user follow statistics.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowStatsResponse {
    
    private Long userId;
    private String username;
    private Long followersCount;
    private Long followingCount;
    private Boolean isFollowing; // If the current user is following this user
}
