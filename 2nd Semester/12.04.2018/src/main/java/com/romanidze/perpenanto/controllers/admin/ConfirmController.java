package com.romanidze.perpenanto.controllers.admin;

import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.security.states.UserState;
import com.romanidze.perpenanto.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("User Confirm Controller")
public class ConfirmController {

    private final UserService userService;

    @Autowired
    public ConfirmController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/confirm/{confirm-hash}")
    public ModelAndView confirmUser(@PathVariable("confirm-hash") String confirmHash){

        User user = this.userService.findByConfirmHash(confirmHash)
                                    .orElseThrow(() -> new NullPointerException("User not found"));

        user.setState(UserState.CONFIRMED);
        this.userService.saveOrUpdate(user);
        return new ModelAndView("auth/success_registration");

    }


}
