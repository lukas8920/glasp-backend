package org.kehrbusch.repositories.queries;

import org.kehrbusch.entities.User;
import org.kehrbusch.entities.VerificationTokenH2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenH2Repository extends CrudRepository<VerificationTokenH2, Long> {
    VerificationTokenH2 findByToken(String token);
}
