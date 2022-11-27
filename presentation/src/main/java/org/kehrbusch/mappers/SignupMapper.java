package org.kehrbusch.mappers;

import org.kehrbusch.entities.SignupRequest;
import org.kehrbusch.entities.SignupRequestApi;
import org.springframework.stereotype.Component;

@Component
public class SignupMapper {
    public SignupRequest map(SignupRequestApi signupRequestApi){
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setMail(signupRequestApi.getMail());
        signupRequest.setPassword(signupRequestApi.getPassword());
        return signupRequest;
    }
}
