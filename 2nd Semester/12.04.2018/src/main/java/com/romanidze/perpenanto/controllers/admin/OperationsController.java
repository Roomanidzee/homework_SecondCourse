package com.romanidze.perpenanto.controllers.admin;

import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.services.interfaces.admin.AdminService;
import com.romanidze.perpenanto.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("Admin Operations Controller")
@RequestMapping("/admin/action")
public class OperationsController {

    private final AdminService adminService;
    private final UserService userService;

    @Autowired
    public OperationsController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("/create_temp_password/{user-id}")
    public ModelAndView getNewPasswordOfUserPage(@PathVariable("user-id") Long userId){

        Optional<User> existedUser = this.userService.findById(userId);

        if(!existedUser.isPresent()){
            throw new NullPointerException();
        }

        this.adminService.createTempPassword(userId);
        return new ModelAndView("email/temp_password_page");

    }


}
