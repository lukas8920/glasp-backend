package org.kehrbusch.controllers;

import org.kehrbusch.LoginService;
import org.kehrbusch.entities.BadRequestException;
import org.kehrbusch.entities.MessageResponse;
import org.kehrbusch.entities.MessageResponseApi;
import org.kehrbusch.entities.PasswordChange;
import org.kehrbusch.mappers.MessageResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/web/auth")
@CrossOrigin
public class PasswordController {
    private static final Logger logger = Logger.getLogger(PasswordController.class.getName());

    private final LoginService loginService;
    private final MessageResponseMapper messageResponseMapper;

    @Autowired
    public PasswordController(LoginService loginService, MessageResponseMapper messageResponseMapper){
        this.loginService = loginService;
        this.messageResponseMapper = messageResponseMapper;
    }

    @PostMapping(path = "/resetPassword", consumes = "text/plain")
    public MessageResponseApi resetPassword(@RequestBody String mail){
        MessageResponse response = this.loginService.resetPassword(mail);
        return this.messageResponseMapper.map(response);
    }

    @PostMapping("/savePassword")
    public MessageResponseApi savePassword(@RequestBody PasswordChange passwordChange) throws BadRequestException {
        return new MessageResponseApi(this.loginService.savePassword(passwordChange));
    }

    @PostMapping("/changePassword")
    public MessageResponseApi changePassword(@RequestBody PasswordChange passwordChange) throws BadRequestException {
        return new MessageResponseApi(this.loginService.changePassword(passwordChange));
    }
}
