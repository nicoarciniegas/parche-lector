package com.parchelector.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating user profile.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    
    @Size(max = 32, message = "Username must be at most 32 characters")
    private String username;
    
    @Size(max = 1000, message = "Bio must be at most 1000 characters")
    private String bio;
    
    @Size(max = 512, message = "Avatar URL must be at most 512 characters")
    private String avatarUrl;
}
