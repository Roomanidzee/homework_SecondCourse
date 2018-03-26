package ru.itis.romanov_andrey.perpenanto.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.Profile;
import ru.itis.romanov_andrey.perpenanto.models.User;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempProfile;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProfileInfoServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProfileServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.UsersServiceInterface;

import java.util.List;

@Controller("Profile Admin Controller")
public class ProfileAdminController {

    @Autowired
    private ProfileServiceInterface profileService;

    @Autowired
    private UsersServiceInterface usersService;

    @Autowired
    private ProfileInfoServiceInterface profileInfoService;

    @RequestMapping(value = "/admin/profiles", method = RequestMethod.GET)
    public ModelAndView getSortedProfiles(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        List<TempProfile> profiles = this.profileService.getProfilesByCookie(cookieValue);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profiles", profiles);
        modelAndView.setViewName("admin/profiles_admin");

        return modelAndView;

    }

    @RequestMapping(value = "/admin/profiles", params = "form_action", method = RequestMethod.POST)
    public ModelAndView workWithProfiles(@RequestParam("form_action") String action,
                                         @RequestParam(value = "id", defaultValue = "0") Long id,
                                         @RequestParam(value = "personName", defaultValue = "") String personName,
                                         @RequestParam(value = "personSurname", defaultValue = "") String personSurname,
                                         @RequestParam(value = "user_id", defaultValue = "") Long userId,
                                         @RequestParam(value = "address_id", defaultValue = "") Long addressId)
    {

        User user = this.usersService.getUserByID(userId);

        Profile profile = Profile.builder()
                                 .id(id)
                                 .personName(personName)
                                 .personSurname(personSurname)
                                 .user(user)
                                 .profileInfo(this.profileInfoService.findAddressByUser(user))
                                 .build();

        switch(action){

            case "add":
                this.profileService.addProfile(profile);
                break;
            case "update":
                this.profileService.updateProfile(profile);
                break;
            case "delete":
                this.profileService.deleteProfile(id);
                break;

        }

        List<TempProfile> profiles = this.profileService.getProfiles();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profiles", profiles);
        modelAndView.setViewName("admin/profiles_admin");

        return modelAndView;

    }

}
