package org.kehrbusch.repositories.impl;

import org.kehrbusch.entities.PasswordResetToken;
import org.kehrbusch.entities.PasswordResetTokenH2;
import org.kehrbusch.mappers.PasswordResetTokenH2Mapper;
import org.kehrbusch.repositories.PasswordResetTokenRepository;
import org.kehrbusch.repositories.queries.PasswordResetTokenH2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetTokenRepositoryImpl implements PasswordResetTokenRepository {
    private final PasswordResetTokenH2Mapper passwordResetTokenH2Mapper;
    private final PasswordResetTokenH2Repository passwordResetTokenH2Repository;

    @Autowired
    public PasswordResetTokenRepositoryImpl(PasswordResetTokenH2Repository passwordResetTokenH2Repository, PasswordResetTokenH2Mapper passwordResetTokenH2Mapper){
        this.passwordResetTokenH2Mapper = passwordResetTokenH2Mapper;
        this.passwordResetTokenH2Repository = passwordResetTokenH2Repository;
    }

    @Override
    public void save(PasswordResetToken passwordResetToken) {
        passwordResetTokenH2Repository.save(this.passwordResetTokenH2Mapper.map(passwordResetToken));
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        PasswordResetTokenH2 passwordResetTokenH2 = this.passwordResetTokenH2Repository.findByToken(token);
        return passwordResetTokenH2 == null ? null : this.passwordResetTokenH2Mapper.map(passwordResetTokenH2);
    }
}
