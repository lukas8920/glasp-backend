package org.kehrbusch;

import org.kehrbusch.entities.Role;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class DataLoader implements ApplicationRunner {
    private static final Logger logger = Logger.getLogger(DataLoader.class.getName());

    private final JwtTokenProvider jwtTokenProvider;

    public DataLoader(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String token = this.jwtTokenProvider.createServerToken(1L, Role.SERVER.toString());
        logger.info("Bearer " + token);
    }
}
