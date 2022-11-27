package org.kehrbusch;

import org.kehrbusch.failures.service.LoginAttemptService;
import org.kehrbusch.failures.Publisher;
import org.kehrbusch.failures.service.RegisterAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final Publisher publisher;
    private final LoginAttemptService loginAttemptService;
    private final RegisterAttemptService registerAttemptService;

    @Autowired
    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider, RegisterAttemptService registerAttemptService,
                             Publisher publisher, LoginAttemptService loginAttemptService){
        this.jwtTokenProvider = jwtTokenProvider;
        this.publisher = publisher;
        this.loginAttemptService = loginAttemptService;
        this.registerAttemptService = registerAttemptService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();

        httpSecurity.cors();

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeRequests()
                .antMatchers("/web/auth/register").permitAll()
                .antMatchers("/web/auth/registrationConfirm").permitAll()
                .antMatchers("/web/auth/login").permitAll()
                .antMatchers("/web/auth/resetPassword").permitAll()
                .antMatchers("/web/auth/savePassword").permitAll()
                .anyRequest().authenticated();

        httpSecurity.apply(new JwtTokenFilterConfigurer(jwtTokenProvider, publisher, loginAttemptService, registerAttemptService));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*deprecated setup for WebSecurityConfigurerAdapter*/
    /*@Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();

        httpSecurity.cors();

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeRequests()
                .antMatchers("/web/auth/register").permitAll()
                .antMatchers("/web/auth/registrationConfirm").permitAll()
                .antMatchers("/web/auth/login").permitAll()
                .antMatchers("/web/auth/resetPassword").permitAll()
                .antMatchers("/web/auth/savePassword").permitAll()
                .anyRequest().authenticated();

        httpSecurity.apply(new JwtTokenFilterConfigurer(jwtTokenProvider, publisher, loginAttemptService, registerAttemptService));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/
}
