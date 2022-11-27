package org.kehrbusch.repositories.queries;

import org.kehrbusch.entities.PasswordResetTokenH2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenH2Repository extends CrudRepository<PasswordResetTokenH2, Long> {
    PasswordResetTokenH2 findByToken(String token);
}
