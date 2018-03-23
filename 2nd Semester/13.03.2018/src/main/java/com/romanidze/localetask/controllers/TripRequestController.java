package com.romanidze.localetask.controllers;

import com.romanidze.localetask.forms.TripRequestForm;
import com.romanidze.localetask.services.interfaces.TripRequestService;
import com.romanidze.localetask.validators.TripRequestFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 23.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller
public class TripRequestController {

    private final TripRequestService requestService;
    private final TripRequestFormValidator tripRequestFormValidator;

    @Autowired
    public TripRequestController(TripRequestService requestService, TripRequestFormValidator tripRequestFormValidator) {
        this.requestService = requestService;
        this.tripRequestFormValidator = tripRequestFormValidator;
    }

    @InitBinder("requestForm")
    public void initUserFormValidator(WebDataBinder binder) {
        binder.addValidators(this.tripRequestFormValidator);
    }

    @GetMapping("/form_example")
    public ModelAndView getExamplePage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("tripRequestForm", new TripRequestForm());
        modelAndView.addObject("requests", this.requestService.getAllRequests());

        return modelAndView;

    }

    @PostMapping("/form_example")
    public String performRequest(@Valid @ModelAttribute TripRequestForm tripRequestForm,
                                 BindingResult errors, RedirectAttributes attributes, ModelMap map){

        if(errors.hasErrors()){

            return "redirect:/" + MvcUriComponentsBuilder.fromMappingName("DC#form_example")
                                                         .build();

        }

        this.requestService.saveRequest(tripRequestForm);
        map.put("tripRequestForm", new TripRequestForm());
        map.put("requests", this.requestService.getAllRequests());
        return "index";

    }

}
