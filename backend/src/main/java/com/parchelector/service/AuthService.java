package com.parchelector.service;

import com.parchelector.dto.request.LoginRequest;
import com.parchelector.dto.request.RegisterRequest;
import com.parchelector.dto.response.AuthResponse;
import com.parchelector.model.entity.PasswordResetToken;
import com.parchelector.model.entity.User;
import com.parchelector.repository.PasswordResetTokenRepository;
import com.parchelector.repository.UserRepository;
import com.parchelector.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Service for authentication operations.
 * 
 * @author Nicolas Arciniegas
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Register a new user.
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Validate username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Validate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        // Generate token
        String token = tokenProvider.generateTokenFromUsername(savedUser.getUsername());

        return new AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    /**
     * Login a user.
     */
    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get user details
        User user = userRepository.findByUsernameOrEmail(
                        request.getUsernameOrEmail(),
                        request.getUsernameOrEmail()
                )
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Generate token
        String token = tokenProvider.generateToken(authentication);

        return new AuthResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    /**
     * Request password reset - generates token and sends email.
     */
    @Transactional
    public void requestPasswordReset(String email) {
        // Find user by email - don't throw exception to prevent email enumeration
        User user = userRepository.findByEmail(email).orElse(null);
        
        if (user == null) {
            // Don't reveal that email doesn't exist, just return silently
            return;
        }

        // Delete any existing tokens for this user
        tokenRepository.deleteByUserId(user.getId());

        // Generate secure random token
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        // Create and save token
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setTokenHash(passwordEncoder.encode(token));
        resetToken.setExpiresAt(LocalDateTime.now().plusHours(1));
        tokenRepository.save(resetToken);

        // Try to send email - if it fails, log but don't throw exception
        try {
            emailService.sendPasswordResetEmail(user.getEmail(), user.getUsername(), token);
        } catch (Exception e) {
            // Log error but don't expose it to the user
            System.err.println("Failed to send password reset email: " + e.getMessage());
            throw new RuntimeException("Failed to send password reset email. Please check email configuration.");
        }
    }

    /**
     * Confirm password reset using token.
     */
    @Transactional
    public void confirmPasswordReset(String token, String newPassword) {
        // Find all tokens and check which one matches
        PasswordResetToken resetToken = tokenRepository.findAll().stream()
                .filter(t -> passwordEncoder.matches(token, t.getTokenHash()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired reset token"));

        // Validate token
        if (resetToken.isUsed()) {
            throw new IllegalArgumentException("This reset token has already been used");
        }

        if (resetToken.isExpired()) {
            throw new IllegalArgumentException("This reset token has expired");
        }

        // Update user password
        User user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Mark token as used
        resetToken.setUsedAt(LocalDateTime.now());
        tokenRepository.save(resetToken);
    }
}
