package org.kehrbusch.mappers;

import org.kehrbusch.entities.PasswordResetToken;
import org.kehrbusch.entities.PasswordResetTokenH2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetTokenH2Mapper {
    private final UserH2Mapper userH2Mapper;

    @Autowired
    public PasswordResetTokenH2Mapper(UserH2Mapper userH2Mapper){
        this.userH2Mapper = userH2Mapper;
    }

    public PasswordResetToken map(PasswordResetTokenH2 passwordResetTokenH2){
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setId(passwordResetTokenH2.getId());
        passwordResetToken.setToken(passwordResetTokenH2.getToken());
        passwordResetToken.setUser(this.userH2Mapper.map(passwordResetTokenH2.getUser()));
        passwordResetToken.setExpiryDate(passwordResetTokenH2.getExpiryDate());
        return passwordResetToken;
    }

    public PasswordResetTokenH2 map(PasswordResetToken passwordResetToken){
        PasswordResetTokenH2 passwordResetTokenH2 = new PasswordResetTokenH2();
        passwordResetTokenH2.setId(passwordResetToken.getId());
        passwordResetTokenH2.setToken(passwordResetToken.getToken());
        passwordResetTokenH2.setUser(this.userH2Mapper.map(passwordResetToken.getUser()));
        passwordResetTokenH2.setExpiryDate(passwordResetToken.getExpiryDate());
        return passwordResetTokenH2;
    }
}
