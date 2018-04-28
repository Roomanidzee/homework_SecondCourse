package com.romanidze.springbootoauth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 27.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@RestController
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<String> getPrincipal(Principal principal){
        return ResponseEntity.ok(principal.getName());
    }

}
