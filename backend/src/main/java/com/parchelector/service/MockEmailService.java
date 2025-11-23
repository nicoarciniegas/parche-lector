package com.parchelector.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Mock Email Service for development/testing without real email.
 * Replace EmailService with this in AuthService to test without email setup.
 * 
 * @author Nicolas Arciniegas
 */
@Service
public class MockEmailService {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    /**
     * Mock sending password reset email - just logs to console.
     */
    public void sendPasswordResetEmail(String toEmail, String username, String token) {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║          🔒 PASSWORD RESET EMAIL (MOCK)                       ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║  To: " + toEmail);
        System.out.println("║  Username: " + username);
        System.out.println("║");
        System.out.println("║  Reset Link:");
        System.out.println("║  " + frontendUrl + "/reset-password?token=" + token);
        System.out.println("║");
        System.out.println("║  Token: " + token);
        System.out.println("║");
        System.out.println("║  This link expires in 1 hour.");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
    }
}
