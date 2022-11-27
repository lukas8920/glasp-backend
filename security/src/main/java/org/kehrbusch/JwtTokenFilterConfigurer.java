package org.kehrbusch;

import org.kehrbusch.failures.service.LoginAttemptService;
import org.kehrbusch.failures.Publisher;
import org.kehrbusch.failures.service.RegisterAttemptService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;
    private final Publisher publisher;
    private final LoginAttemptService service;
    private final RegisterAttemptService registerAttemptService;

    public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider, Publisher publisher,
                                    LoginAttemptService service, RegisterAttemptService registerAttemptService){
        this.jwtTokenProvider = jwtTokenProvider;
        this.publisher = publisher;
        this.service = service;
        this.registerAttemptService = registerAttemptService;
    }

    @Override
    public void configure(HttpSecurity httpSecurity){
        //httpSecurity.addFilterBefore(new IpFilter(), SecurityContextHolderFilter.class);
        JwtTokenFilter filter = new JwtTokenFilter(registerAttemptService, jwtTokenProvider, publisher, service);
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
