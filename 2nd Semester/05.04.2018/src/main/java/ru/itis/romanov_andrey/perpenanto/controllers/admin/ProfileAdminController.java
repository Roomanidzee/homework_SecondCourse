package ru.itis.romanov_andrey.perpenanto.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.Product;
import ru.itis.romanov_andrey.perpenanto.models.Profile;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProductServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProfileServiceInterface;

import java.util.List;

/**
 * 08.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("Profile Admin Controller")
public class ProfileAdminController {

    private final ProfileServiceInterface profileService;
    private final ProductServiceInterface productService;

    @Autowired
    public ProfileAdminController(ProfileServiceInterface profileService, ProductServiceInterface productService) {
        this.profileService = profileService;
        this.productService = productService;
    }

    @GetMapping("/admin/profiles")
    public ModelAndView getSortedProfiles(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        List<Profile> profiles = this.profileService.getProfilesByCookie(cookieValue);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profiles", profiles);
        modelAndView.setViewName("admin/profiles_admin");

        return modelAndView;

    }

    @PostMapping(value = "/admin/profiles", params = "form_action")
    public ModelAndView workWithProfiles(@RequestParam("form_action") String action,
                                         @ModelAttribute Profile profile){

        if (action.equals("add") || action.equals("update")) {
            this.profileService.saveOrUpdate(profile);
        } else if (action.equals("delete")) {
            this.profileService.delete(profile.getId());
        }

        List<Profile> profiles = this.profileService.getProfiles();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profiles", profiles);
        modelAndView.setViewName("admin/profiles_admin");

        return modelAndView;

    }

    @PostMapping(value = "/admin/profiles/{profile}/{product}")
    public ModelAndView workWithProfiles(Profile profile, Product product){

        product.setProfile(profile);
        this.productService.saveOrUpdate(product);
        List<Profile> profiles = this.profileService.getProfiles();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profiles", profiles);
        modelAndView.setViewName("admin/profiles_admin");

        return modelAndView;

    }

}
