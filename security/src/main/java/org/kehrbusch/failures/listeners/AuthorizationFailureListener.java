package org.kehrbusch.failures.listeners;

import org.kehrbusch.failures.Publisher;
import org.kehrbusch.failures.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Component
public class AuthorizationFailureListener implements ApplicationListener<Publisher.AuthorizationFailureEvent> {
    private static final Logger logger = Logger.getLogger(AuthorizationFailureListener.class.getName());

    private final LoginAttemptService loginAttemptService;

    @Autowired
    public AuthorizationFailureListener(LoginAttemptService loginAttemptService){
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onApplicationEvent(Publisher.AuthorizationFailureEvent event) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            loginAttemptService.loginFailed(request.getRemoteAddr());
        } else {
            loginAttemptService.loginFailed(xfHeader.split(",")[0]);
        }
    }
}
