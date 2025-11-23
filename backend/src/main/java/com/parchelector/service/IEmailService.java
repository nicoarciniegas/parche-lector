package com.parchelector.service;

/**
 * Interface for email services.
 * Allows switching between real email (EmailService) and mock email (MockEmailService).
 * 
 * @author Nicolas Arciniegas
 */
public interface IEmailService {
    
    /**
     * Send password reset email with token.
     * 
     * @param toEmail recipient email address
     * @param username recipient username
     * @param token password reset token
     */
    void sendPasswordResetEmail(String toEmail, String username, String token);
}
