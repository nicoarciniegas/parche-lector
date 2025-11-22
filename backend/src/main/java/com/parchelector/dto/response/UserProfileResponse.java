package com.parchelector.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for user profile information.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private String userName;
    private String userAvatar;
    private String bio;
    private Long followers;
    private Long following;
    private List<UserBookResponse> userBooks;
}
