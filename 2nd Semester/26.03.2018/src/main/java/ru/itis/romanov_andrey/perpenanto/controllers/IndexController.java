package ru.itis.romanov_andrey.perpenanto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("Index Controller Annotation")
public class IndexController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView getIndex(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("price1", 1500);
        modelAndView.addObject("price2", 4000);
        modelAndView.addObject("price3", 300);

        modelAndView.setViewName("index");

        return modelAndView;

    }

}
