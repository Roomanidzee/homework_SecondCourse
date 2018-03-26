package ru.itis.romanov_andrey.perpenanto.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.User;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.UsersServiceInterface;

import java.util.List;

@Controller("UserAdmin Controller Annotation")
public class UsersAdminController {

    @Autowired
    private UsersServiceInterface userService;

    @RequestMapping(value = "/admin/security", method = RequestMethod.GET)
    public ModelAndView getSortedUsers(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        List<User> users = this.userService.getUsers();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/users_admin");

        return modelAndView;
    }

    @RequestMapping(value = "/admin/security", params = "form_action", method = RequestMethod.POST)
    public ModelAndView workWithUsers(@RequestParam("form_action") String action,
                                      @RequestParam(value = "id", defaultValue = "0") Long id,
                                      @RequestParam(value = "username", defaultValue = "") String username,
                                      @RequestParam(value = "password", defaultValue = "") String password)
    {

        User user = User.builder()
                        .id(id)
                        .username(username)
                        .password(password)
                        .build();

        switch(action){

            case "add":
                this.userService.addUser(user);
                break;
            case "update":
                this.userService.updateUser(user);
                break;
            case "delete":
                this.userService.deleteUser(id);
                break;

        }

        List<User> users = this.userService.getUsers();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/users_admin");

        return modelAndView;
    }

}
