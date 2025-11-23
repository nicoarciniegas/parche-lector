package com.parchelector.repository;

import com.parchelector.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository for PasswordResetToken entity.
 * 
 * @author Nicolas Arciniegas
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);
    
    void deleteByExpiresAtBefore(LocalDateTime dateTime);
    
    void deleteByUserId(Long userId);
}
