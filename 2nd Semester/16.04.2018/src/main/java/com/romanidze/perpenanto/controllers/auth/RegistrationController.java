package com.romanidze.perpenanto.controllers.auth;

import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.user.UserRegistrationForm;
import com.romanidze.perpenanto.services.interfaces.admin.AdminService;
import com.romanidze.perpenanto.services.interfaces.auth.RegistrationService;
import com.romanidze.perpenanto.services.interfaces.newsletter.SMSService;
import com.romanidze.perpenanto.services.interfaces.user.ProfileService;
import com.romanidze.perpenanto.services.interfaces.user.UserService;
import com.romanidze.perpenanto.validators.UserRegistrationFormValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("Registration Controller Annotation")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private RegistrationService registrationService;
    private UserService userService;
    private ProfileService profileService;
    private SMSService smsService;
    private AdminService adminService;
    private UserRegistrationFormValidator userRegistrationFormValidator;

    @InitBinder
    public void initUserFormValidator(WebDataBinder binder){
        binder.addValidators(this.userRegistrationFormValidator);
    }

    @PostMapping(value = "/register")
    public String register(@Valid @ModelAttribute("userForm") UserRegistrationForm userRegistrationForm,
                           BindingResult errors, RedirectAttributes attributes){

        if(errors.hasErrors()){

            attributes.addFlashAttribute("error", errors.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/register";

        }

        this.registrationService.register(userRegistrationForm);

        Optional<User> newUser = this.userService.findByLogin(userRegistrationForm.getLogin());

        newUser.ifPresent(user -> {

            this.adminService.generateHash(user);

            Profile profile = this.profileService.findByUserId(user.getId());
            StringBuilder sb = new StringBuilder();
            sb.append("Здравствуйте, ").append(profile.getPersonSurname()).append(" ").append(profile.getPersonName())
              .append("! Удачных вам покупок!");

            if(!(this.smsService.sendSMS(profile.getPhone(),sb.toString()))){
                throw new NullPointerException("Ничего не отправлено!");
            }

        });

        return "auth/confirm_registration";

    }

    @GetMapping(value = "/register")
    public ModelAndView getRegisterPage(){
        return new ModelAndView("auth/registration");
    }

}
