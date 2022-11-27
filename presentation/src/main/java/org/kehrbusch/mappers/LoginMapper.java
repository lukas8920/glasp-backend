package org.kehrbusch.mappers;

import org.kehrbusch.entities.LoginRequest;
import org.kehrbusch.entities.LoginRequestApi;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {
    public LoginRequest map(LoginRequestApi loginRequestApi){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMail(loginRequestApi.getMail());
        loginRequest.setPassword(loginRequestApi.getPassword());
        return loginRequest;
    }
}
