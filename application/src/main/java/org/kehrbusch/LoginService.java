package org.kehrbusch;

import org.kehrbusch.entities.*;
import org.kehrbusch.failures.Publisher;
import org.kehrbusch.repositories.PasswordResetTokenRepository;
import org.kehrbusch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class LoginService {
    private final Logger logger = Logger.getLogger(LoginService.class.getName());

    private final Publisher publisher;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder encoder;

    @Value("${server.host}")
    private String host;

    @Autowired
    public LoginService(UserRepository userRepository, AuthenticationManager authenticationManager,
                        JwtTokenProvider jwtTokenProvider, PasswordResetTokenRepository passwordResetTokenRepository,
                        JavaMailSender javaMailSender, PasswordEncoder encoder, Publisher publisher){
        this.encoder = encoder;
        this.javaMailSender = javaMailSender;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = jwtTokenProvider;
        this.publisher = publisher;
    }

    public JwtResponse login(LoginRequest loginRequest) throws BadRequestException {
        User user = this.userRepository.findByMail(loginRequest.getMail());
        if (user == null){
            logger.warning("User with mail " + loginRequest.getMail() + " is invalid.");
            this.publisher.publishAuthorizationFailure();
            throw new BadRequestException("There is no User with the provided mail address.");
        }

        Authentication authentication;
        try {
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getId(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e){
            logger.warning("Password for user " + user.getMail() + " is invalid.");
            this.publisher.publishAuthorizationFailure();
            throw new BadRequestException("Password is invalid.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = tokenProvider.createUserToken(Long.valueOf(userDetails.getUsername()), "user");
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        logger.info("Jwt: " + jwt);
        this.publisher.publishAuthorizationSuccess();
        return new JwtResponse(jwt,
                user.getMail(),
                roles);
    }

    public MessageResponse resetPassword(String userMail){
        User user = this.userRepository.findByMail(userMail);
        if (user == null){
            logger.warning("User with mail " + userMail + " is invalid.");
            this.publisher.publishAuthorizationFailure();
            return new MessageResponse("Please check your mails to reset your password.");
        }
        logger.info("Create reset token for user " + user.getMail());
        this.publisher.publishAuthorizationSuccess();
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        javaMailSender.send(constructResetTokenEmail(token, user));
        return new MessageResponse("Please check your mails to reset your password.");
    }

    public String savePassword(PasswordChange passwordChange) throws BadRequestException{
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(passwordChange.getToken());

        if (!isTokenFound(passToken) || isTokenExpired(passToken)){
            logger.warning("Password change request for token " + passwordChange.getToken() + " is invalid.");
            this.publisher.publishAuthorizationFailure();
            throw new BadRequestException("No permission to access glasp service.");
        }

        User user = passToken.getUser();
        String password = encoder.encode(passwordChange.getNewPassword());
        this.userRepository.changePassword(password, user.getId());
        logger.info("Changed password for user " + user.getMail());
        this.publisher.publishAuthorizationSuccess();
        javaMailSender.send(constructEmail("Your password has been changed. Please notify us, if you did not trigger the change.", user, "Password changed"));
        return "ok";
    }

    public String changePassword(PasswordChange passwordChange) throws BadRequestException{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long user = Long.valueOf(userDetails.getUsername());
        logger.info("User " + user);
        User tmpUser = this.userRepository.findById(user);
        if (tmpUser == null) throw new BadRequestException("No permission to change password.");
        this.userRepository.changePassword(encoder.encode(passwordChange.getNewPassword()), tmpUser.getId());
        javaMailSender.send(constructEmail("Your password has been changed. Please notify us, if you did not trigger the change.", tmpUser, "Password changed"));
        return "Password has been successfully changed";
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    private void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    private SimpleMailMessage constructResetTokenEmail(String token, User user) {
        String url = this.host + "/pw-change/" + token;
        String message = "Please click on the link to reset your account.";
        return constructResetMail(message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructResetMail(String body, User user){
        return this.constructEmail(body, user, "Reset Password");
    }

    private SimpleMailMessage constructEmail(String body, User user, String subject) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getMail());
        email.setFrom("glasp.info@gmail.com");
        return email;
    }
}
