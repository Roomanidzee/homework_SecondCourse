package com.romanidze.formvalidatetask.controllers;

import com.romanidze.formvalidatetask.utils.SmileHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 09.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller
public class SmileController {

    @GetMapping("/smiles")
    public ModelAndView getSmilesPage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Эмоджи");
        modelAndView.setViewName("emoji_page");

        return modelAndView;

    }

    @PostMapping("/smiles")
    public ModelAndView getSmiles(@RequestParam(value = "smile_type", defaultValue = ":winky:") String smileType){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Эмоджи");
        modelAndView.addObject("smile", smileType);
        modelAndView.addObject("smileHelper", new SmileHelper());
        modelAndView.setViewName("emoji_page");

        return modelAndView;

    }
}
