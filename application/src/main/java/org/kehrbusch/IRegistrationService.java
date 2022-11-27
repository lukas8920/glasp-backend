package org.kehrbusch;

import org.kehrbusch.entities.*;

public interface IRegistrationService {
    MessageResponse register(SignupRequest signupRequest) throws BadRequestException;
    User getUser(String verificationToken);
    User saveRegisteredUser(User user);
    void createVerificationToken(User user, String token);
    VerificationToken getVerificationToken(String verificationToken);

}
