package org.kehrbusch.repositories;

import org.kehrbusch.entities.PasswordResetToken;

public interface PasswordResetTokenRepository {
    void save(PasswordResetToken passwordResetToken);
    PasswordResetToken findByToken(String token);
}
