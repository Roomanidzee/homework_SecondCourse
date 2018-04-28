package com.romanidze.springmvcoauth.controllers;

import com.romanidze.springmvcoauth.security.utils.OAuthAuthentication;
import com.romanidze.springmvcoauth.services.interfaces.UserService;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.MessageFormat;

/**
 * 28.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller
public class UserController {

    private final Environment environment;
    private final UserService userService;

    @Autowired
    public UserController(Environment environment, UserService userService) {
        this.environment = environment;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String redirectToLoginPage(){

        String oauthURL = this.environment.getProperty("vk.application.oauth_url");
        Integer clientID = Integer.parseInt(this.environment.getProperty("vk.application.client_id"));

        String redirectURL = MessageFormat.format(oauthURL, clientID);

        StringBuilder sb = new StringBuilder();
        sb.append("redirect:").append(redirectURL);
        return sb.toString();

    }

    @GetMapping("/user_page")
    public ResponseEntity<UserXtrCounters> getUserInfo(Authentication authentication){

        OAuthAuthentication oauthAuthentication = (OAuthAuthentication) authentication;

        return ResponseEntity.ok(this.userService.getUserInfo(oauthAuthentication));

    }

}
