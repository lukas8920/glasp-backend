package org.kehrbusch.repositories.impl;

import org.kehrbusch.entities.User;
import org.kehrbusch.entities.VerificationToken;
import org.kehrbusch.entities.VerificationTokenH2;
import org.kehrbusch.mappers.VerificationH2Mapper;
import org.kehrbusch.repositories.VerificationTokenRepository;
import org.kehrbusch.repositories.queries.VerificationTokenH2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerificationTokenRepositoryImpl implements VerificationTokenRepository {
    private final VerificationH2Mapper mapper;
    private final VerificationTokenH2Repository verificationTokenH2Repository;

    @Autowired
    public VerificationTokenRepositoryImpl(VerificationH2Mapper verificationH2Mapper, VerificationTokenH2Repository verificationTokenH2Repository){
        this.mapper = verificationH2Mapper;
        this.verificationTokenH2Repository = verificationTokenH2Repository;
    }

    @Override
    public VerificationToken findByToken(String token) {
        VerificationTokenH2 tokenH2 = this.verificationTokenH2Repository
                .findByToken(token);
        return tokenH2 != null ? this.mapper.map(tokenH2) : null;
    }

    @Override
    public VerificationToken findByUser(User user) {
        return null;
    }

    @Override
    public VerificationToken save(VerificationToken token) {
        VerificationTokenH2 verificationTokenH2 = this.verificationTokenH2Repository.save(this.mapper.map(token));
        return this.mapper.map(verificationTokenH2);
    }
}
