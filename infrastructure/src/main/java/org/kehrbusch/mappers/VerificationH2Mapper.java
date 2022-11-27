package org.kehrbusch.mappers;

import org.kehrbusch.entities.VerificationToken;
import org.kehrbusch.entities.VerificationTokenH2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerificationH2Mapper {
    private final UserH2Mapper mapper;

    @Autowired
    public VerificationH2Mapper(UserH2Mapper userH2Mapper){
        this.mapper = userH2Mapper;
    }

    public VerificationTokenH2 map(VerificationToken verificationToken){
        VerificationTokenH2 verificationTokenH2 = new VerificationTokenH2();
        verificationTokenH2.setId(verificationToken.getId());
        verificationTokenH2.setToken(verificationToken.getToken());
        verificationTokenH2.setDate(verificationToken.getExpiryDate());
        verificationTokenH2.setUser(mapper.map(verificationToken.getUser()));
        return verificationTokenH2;
    }

    public VerificationToken map(VerificationTokenH2 verificationTokenH2){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setId(verificationTokenH2.getId());
        verificationToken.setToken(verificationTokenH2.getToken());
        verificationToken.setExpiryDate(verificationTokenH2.getDate());
        verificationToken.setUser(this.mapper.map(verificationTokenH2.getUser()));
        return verificationToken;
    }
}
