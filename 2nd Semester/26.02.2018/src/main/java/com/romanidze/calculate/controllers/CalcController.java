package com.romanidze.calculate.controllers;

import com.romanidze.calculate.forms.CalcAction;
import com.romanidze.calculate.services.interfaces.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 26.02.2018
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
        return new ModelAndView("calculate");
    }

    @PostMapping("/calc")
    public ModelAndView calculate(@ModelAttribute("calculateForm") CalcAction calculateForm){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("calculate");
        modelAndView.addObject("result", this.calcService.getCalcResult(calculateForm));

        return modelAndView;

    }

}
