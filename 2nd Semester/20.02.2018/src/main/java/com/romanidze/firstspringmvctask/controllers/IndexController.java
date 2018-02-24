package com.romanidze.firstspringmvctask.controllers;

import com.romanidze.firstspringmvctask.domain.User;
import com.romanidze.firstspringmvctask.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 24.02.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("Index Controller")
public class IndexController {

    private UserService userService;

    @Autowired
    public IndexController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("/simple")
    @ResponseBody
    public String getSimpleMessage(){

        User user = User.builder()
                        .login("test")
                        .password("pass")
                        .build();

        return user.toString();

    }

    @RequestMapping("/complex")
    @ResponseBody
    public String getComplexMessage(){
        return this.userService.getUserById(1L).toString();
    }

}
