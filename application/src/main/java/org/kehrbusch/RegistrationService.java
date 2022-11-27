package org.kehrbusch;

import org.kehrbusch.entities.*;
import org.kehrbusch.failures.Publisher;
import org.kehrbusch.repositories.ApiAccessRepository;
import org.kehrbusch.repositories.UserRepository;
import org.kehrbusch.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class RegistrationService implements IRegistrationService {
    private static final Logger logger = Logger.getLogger(RegistrationService.class.getName());

    private final Publisher authorizationPublisher;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JavaMailSender mailSender;
    private final ApiAccessRepository apiAccessRepository;

    @Value("${server.host}")
    private String host;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                               ApplicationEventPublisher publisher, VerificationTokenRepository verificationTokenRepository,
                               JavaMailSender mailSender, ApiAccessRepository apiAccessRepository, Publisher authorizationPublisher){
        this.mailSender = mailSender;
        this.publisher = publisher;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.apiAccessRepository = apiAccessRepository;
        this.authorizationPublisher = authorizationPublisher;
    }

    @Override
    public MessageResponse register(SignupRequest signupRequest) throws BadRequestException {
        if (this.userRepository.existsByUsername(signupRequest.getMail())){
            logger.warning("Registration for user " + signupRequest.getMail() + " is invalid as user already exists.");
            this.authorizationPublisher.publishRegistrationFailure();
            throw new BadRequestException("Error: User Mail is already assigned to an account.");
        }

        List<Role> roles = List.of(Role.USER);
        User user = new User(null, signupRequest.getMail(), this.passwordEncoder.encode(signupRequest.getPassword())
                , roles, false);

        user = saveRegisteredUser(user);
        this.publisher.publishEvent(new OnRegistrationCompleteEvent(user, LocaleContextHolder.getLocale(), "/web/auth"));
        logger.info("Registration was success, but still increment to avoid Brute Force on registration endpoint.");
        this.authorizationPublisher.publishRegistrationFailure();

        return new MessageResponse("Please check your mails to validate your account and to complete the registration.");
    }

    public String confirmRegistration(String token){
        VerificationToken verificationToken = this.getVerificationToken(token);
        if (verificationToken == null) {
            logger.warning("Verification of account has failed.");
            this.authorizationPublisher.publishAuthorizationFailure();
            return this.host + "/unauth";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            logger.warning("Verification of account has failed - token has expired.");
            this.authorizationPublisher.publishAuthorizationFailure();
            return this.host + "/unauth";
        }

        user.setEnabled(true);
        this.saveRegisteredUser(user);
        logger.info("Verification of account was successful");
        this.authorizationPublisher.publishAuthorizationSuccess();

        //enable user password and user key
        ApiAccess apiAccess = createCredentials(user);
        this.apiAccessRepository.save(apiAccess);

        return this.host + "/login";
    }

    private ApiAccess createCredentials(User user){
        UUID userKey = UUID.randomUUID();
        return new ApiAccess(user.getId(), userKey.toString(), "");
    }

    public MessageResponse resendVerificationToken(String existingToken){
        VerificationToken newToken = generateNewVerificationToken(existingToken);
        User user = getUser(newToken.getToken());

        String message = "Please click on the link to complete the registration of your account.";
        SimpleMailMessage email =
                constructResendVerificationTokenEmail(message, newToken, user);
        mailSender.send(email);

        return new MessageResponse("Successful SignUp. Please check your mails to validate your account and to complete the registration.");
    }

    @Override
    public User getUser(String verificationToken) {
        return this.verificationTokenRepository.findByToken(verificationToken).getUser();
    }

    @Override
    public User saveRegisteredUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        this.verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return this.verificationTokenRepository.findByToken(verificationToken);
    }

    public VerificationToken generateNewVerificationToken(String token){
        VerificationToken verificationToken = this.verificationTokenRepository.findByToken(token);
        String newToken = UUID.randomUUID().toString();
        verificationToken.setToken(newToken);
        return this.verificationTokenRepository.save(verificationToken);
    }

    private SimpleMailMessage constructResendVerificationTokenEmail(String message, VerificationToken newToken, User user) {
        String confirmationUrl = this.host + "/web/auth/registrationConfirm.html?token=" + newToken.getToken();
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(message + "\r\n" + confirmationUrl);
        email.setFrom("glasp.info@gmail.com");
        email.setTo(user.getMail());
        return email;
    }
}
