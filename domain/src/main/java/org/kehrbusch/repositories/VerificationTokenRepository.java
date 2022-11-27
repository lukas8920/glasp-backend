package org.kehrbusch.repositories;

import org.kehrbusch.entities.User;
import org.kehrbusch.entities.VerificationToken;

public interface VerificationTokenRepository {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
    VerificationToken save(VerificationToken token);
}
