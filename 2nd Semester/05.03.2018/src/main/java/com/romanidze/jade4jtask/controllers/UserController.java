package com.romanidze.jade4jtask.controllers;

import com.romanidze.jade4jtask.domain.User;
import com.romanidze.jade4jtask.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 05.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/simple")
    public ModelAndView getSimpleMessage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("simple");

        User user = User.builder()
                        .login("test")
                        .password("pass")
                        .build();

        modelAndView.addObject("title", "Простое отображение");
        modelAndView.addObject("user", user.toString());
        return modelAndView;

    }

    @GetMapping("/complex")
    public ModelAndView getComplexMessage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("complex");
        modelAndView.addObject("title", "Сложное отображение");
        modelAndView.addObject("user", this.userService.getUserById(1L).toString());

        return modelAndView;

    }

}
