package com.romanidze.viewresolvertask.controllers;

import com.romanidze.viewresolvertask.domain.User;
import com.romanidze.viewresolvertask.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 03.03.2018
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

    @GetMapping("/simple")
    public ModelAndView getSimpleMessage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        User user = User.builder()
                        .login("test")
                        .password("pass")
                        .build();

        modelAndView.addObject("user", user.toString());
        return modelAndView;

    }

    @GetMapping("/complex")
    public ModelAndView getComplexMessage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("user", this.userService.getUserById(1L).toString());

        return modelAndView;

    }

}
