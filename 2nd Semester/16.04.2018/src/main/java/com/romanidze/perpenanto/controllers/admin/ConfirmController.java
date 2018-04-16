package com.romanidze.perpenanto.controllers.admin;

import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.security.states.UserState;
import com.romanidze.perpenanto.services.interfaces.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("User Confirm Controller")
public class ConfirmController {

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger(ConfirmController.class);

    @Autowired
    public ConfirmController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/confirm/{confirm-hash}")
    public ModelAndView confirmUser(@PathVariable("confirm-hash") String confirmHash){

        logger.info("Подтверждаем пользователя");

        Optional<User> existedUser = this.userService.findByConfirmHash(confirmHash);

        existedUser.ifPresent(user -> {
            logger.warn("Сохраняем пользователя");
            user.setState(UserState.CONFIRMED);
            this.userService.saveOrUpdate(user);
        });

        if(!existedUser.isPresent()){
            logger.error("User not found!");
            throw new NoSuchElementException();
        }

        return new ModelAndView("auth/success_registration");

    }


}
