package com.romanidze.formvalidatetask.controllers;

import com.romanidze.formvalidatetask.forms.TripRequestForm;
import com.romanidze.formvalidatetask.services.interfaces.TripRequestService;
import com.romanidze.formvalidatetask.validators.TripRequestFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 09.03.2018
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
       modelAndView.setViewName("form_example");
       modelAndView.addObject("title", "Валидация формы");
       modelAndView.addObject("requests", this.requestService.getAllRequests());

       return modelAndView;

   }

   @PostMapping("/form_example")
   public String performRequest(@Valid @ModelAttribute("requestForm") TripRequestForm userRegistrationForm,
                                BindingResult errors, RedirectAttributes attributes, ModelMap map){

       if(errors.hasErrors()){

           List<FieldError> fieldErrors = errors.getFieldErrors();

           Map<String, String> errorMap =
                   fieldErrors.stream()
                              .collect(Collectors.toMap(ObjectError::getObjectName,
                                                        DefaultMessageSourceResolvable::getDefaultMessage,
                                                        (a, b) -> b
                                                       )
                              );

           attributes.addFlashAttribute("title", "Валидация формы");
           attributes.addFlashAttribute("errors", errorMap);
           return "redirect:/form_example";

       }

       map.put("title", "Валидация формы");
       map.put("requests", this.requestService.getAllRequests());
       return "form_example";

   }

}
