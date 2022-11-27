package org.kehrbusch.controllers;

import org.kehrbusch.LoginService;
import org.kehrbusch.RegistrationService;
import org.kehrbusch.entities.*;
import org.kehrbusch.mappers.JwtResponseMapper;
import org.kehrbusch.mappers.LoginMapper;
import org.kehrbusch.mappers.MessageResponseMapper;
import org.kehrbusch.mappers.SignupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.logging.Logger;

@RestController
@RequestMapping("/web/auth")
@CrossOrigin
public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final SignupMapper signupMapper;
    private final JwtResponseMapper jwtResponseMapper;
    private final LoginMapper loginMapper;
    private final MessageResponseMapper messageResponseMapper;

    @Autowired
    public AuthController(RegistrationService registrationService, SignupMapper signupMapper,
                          MessageResponseMapper messageResponseMapper, LoginMapper loginMapper,
                          JwtResponseMapper jwtResponseMapper, LoginService loginService){
        this.jwtResponseMapper = jwtResponseMapper;
        this.loginService = loginService;
        this.loginMapper = loginMapper;
        this.registrationService = registrationService;
        this.signupMapper = signupMapper;
        this.messageResponseMapper = messageResponseMapper;
    }

    @PostMapping(value = "/register")
    public MessageResponseApi register(@RequestBody SignupRequestApi signupRequestApi) throws BadRequestException {
        logger.info("Received registration application.");
        MessageResponse response = registrationService.register(signupMapper.map(signupRequestApi));
        return messageResponseMapper.map(response);
    }

    @GetMapping(value = "/registrationConfirm")
    public RedirectView confirmRegistration(@RequestParam("token") String token){
        RedirectView redirectView = new RedirectView();
        String url = this.registrationService.confirmRegistration(token);
        redirectView.setUrl(url);
        return redirectView;
    }

    @PostMapping(value = "/login")
    public JwtResponseApi login(@RequestBody LoginRequestApi loginRequestApi) throws BadRequestException{
        JwtResponse jwtResponse = this.loginService.login(loginMapper.map(loginRequestApi));
        return jwtResponseMapper.map(jwtResponse);
    }

    @GetMapping(value = "/resendVerificationToken")
    public MessageResponseApi resendVerificationToken(@RequestParam("token") String token){
        MessageResponse response = this.registrationService.resendVerificationToken(token);
        return this.messageResponseMapper.map(response);
    }
}
