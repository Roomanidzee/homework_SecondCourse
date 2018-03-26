package ru.itis.romanov_andrey.perpenanto.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("Admin Controller Annotation")
public class AdminController {

    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public ModelAndView getAdminIndexPage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/admin_page");

        return modelAndView;

    }

}
