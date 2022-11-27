package org.kehrbusch.mappers;

import org.kehrbusch.entities.JwtResponse;
import org.kehrbusch.entities.JwtResponseApi;
import org.springframework.stereotype.Component;

@Component
public class JwtResponseMapper {
    public JwtResponseApi map(JwtResponse jwtResponse){
        JwtResponseApi jwtResponseApi = new JwtResponseApi();
        jwtResponseApi.setEmail(jwtResponse.getEmail());
        jwtResponseApi.setRoles(jwtResponse.getRoles());
        jwtResponseApi.setType(jwtResponse.getType());
        jwtResponseApi.setToken(jwtResponse.getToken());
        return jwtResponseApi;
    }
}
