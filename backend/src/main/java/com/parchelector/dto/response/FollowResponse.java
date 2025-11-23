package com.parchelector.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for follow operations.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponse {
    
    private Long followerId;
    private String followerUsername;
    private Long followedId;
    private String followedUsername;
    private LocalDateTime createdAt;
}
