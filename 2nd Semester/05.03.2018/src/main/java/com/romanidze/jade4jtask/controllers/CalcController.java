package com.romanidze.jade4jtask.controllers;

import com.romanidze.jade4jtask.forms.CalcAction;
import com.romanidze.jade4jtask.services.interfaces.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 05.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller
public class CalcController {

    private CalcService calcService;

    @Autowired
    public CalcController(CalcService calcService){
        this.calcService = calcService;
    }

    @GetMapping("/calc")
    public ModelAndView getCalculationForm(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("calculate");
        modelAndView.addObject("title", "Калькулятор");

        return modelAndView;
    }

    @PostMapping("/calc")
    public ModelAndView calculate(@ModelAttribute("calculateForm") CalcAction calculateForm){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("calculate");
        modelAndView.addObject("result", this.calcService.getCalcResult(calculateForm));
        modelAndView.addObject("title", "Калькулятор");

        return modelAndView;

    }

}
