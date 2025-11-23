package com.parchelector.controller;

import com.parchelector.dto.ApiResponse;
import com.parchelector.dto.request.ForgotPasswordRequest;
import com.parchelector.dto.request.LoginRequest;
import com.parchelector.dto.request.RegisterRequest;
import com.parchelector.dto.request.ResetPasswordRequest;
import com.parchelector.dto.response.AuthResponse;
import com.parchelector.dto.response.UserProfileResponse;
import com.parchelector.model.entity.User;
import com.parchelector.repository.UserRepository;
import com.parchelector.service.AuthService;
import com.parchelector.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
 * REST controller for authentication endpoints.
 * 
 * @author Nicolas Arciniegas
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication endpoints for user registration and login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Register a new user.
     */
    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Create a new user account")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse authResponse = authService.register(request);
            ApiResponse<AuthResponse> response = new ApiResponse<>(
                    "SUCCESS",
                    "User registered successfully",
                    authResponse
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<AuthResponse> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Login user.
     */
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and return JWT token")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse authResponse = authService.login(request);
            ApiResponse<AuthResponse> response = new ApiResponse<>(
                    "SUCCESS",
                    "Login successful",
                    authResponse
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<AuthResponse> response = new ApiResponse<>(
                    "ERROR",
                    "Invalid credentials",
                    null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * Get current authenticated user profile.
     */
    @GetMapping("/me")
    @Operation(summary = "Get current user profile", description = "Get profile data for the authenticated user")
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUserProfile() {
        try {
            // Get authenticated user from security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Find user by username
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Get profile data
            UserProfileResponse profile = userService.getUserProfile(user.getId());

            ApiResponse<UserProfileResponse> response = new ApiResponse<>(
                    "SUCCESS",
                    "Profile retrieved successfully",
                    profile
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<UserProfileResponse> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Request password reset email")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            authService.requestPasswordReset(request.getEmail());
            
            ApiResponse<Void> response = new ApiResponse<>(
                    "SUCCESS",
                    "Password reset email sent. Please check your inbox.",
                    null
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // For security, don't reveal if email exists or not
            ApiResponse<Void> response = new ApiResponse<>(
                    "SUCCESS",
                    "If the email exists, a password reset link has been sent.",
                    null
            );
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Confirm password reset with token")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            authService.confirmPasswordReset(request.getToken(), request.getNewPassword());
            
            ApiResponse<Void> response = new ApiResponse<>(
                    "SUCCESS",
                    "Password has been reset successfully. You can now login with your new password.",
                    null
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
}
