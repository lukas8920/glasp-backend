package org.kehrbusch;

import org.kehrbusch.entities.OnRegistrationCompleteEvent;
import org.kehrbusch.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final IRegistrationService registrationService;
    private final JavaMailSender mailSender;

    @Value("${server.host}")
    private String host;

    @Autowired
    public RegistrationListener(IRegistrationService registrationService, MessageSource messageSource,
                               JavaMailSender mailSender){
        this.registrationService = registrationService;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        registrationService.createVerificationToken(user, token);

        String recipientAddress = user.getMail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = "Please click on the link to complete the registration of your account.";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("glasp.info@gmail.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + this.host + confirmationUrl);
        mailSender.send(email);
    }
}
