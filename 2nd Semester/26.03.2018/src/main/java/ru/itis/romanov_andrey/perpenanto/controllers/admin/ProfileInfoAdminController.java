package ru.itis.romanov_andrey.perpenanto.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.AddressToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempAddressToUser;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProfileInfoServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.UsersServiceInterface;

import java.util.List;

@Controller("ProfileInfo Admin Controller")
public class ProfileInfoAdminController {

    @Autowired
    private ProfileInfoServiceInterface profileInfoService;

    @Autowired
    private UsersServiceInterface userService;

    @RequestMapping(value = "/admin/profile_infos", method = RequestMethod.GET)
    public ModelAndView getSortedProfileInfos(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        List<TempAddressToUser> profileInfos = this.profileInfoService.getProfileInfosByCookie(cookieValue);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profile_infos", profileInfos);
        modelAndView.setViewName("admin/profile_info_admin");

        return modelAndView;

    }

    @RequestMapping(value = "/admin/profile_infos", method = RequestMethod.POST)
    public ModelAndView workWithProfileInfos(@RequestParam("form_action") String action,
                                             @RequestParam(value = "id", defaultValue = "0") Long id,
                                             @RequestParam(value = "user_id", defaultValue = "0") Long userId,
                                             @RequestParam(value = "country", defaultValue = "") String country,
                                             @RequestParam(value = "postIndex", defaultValue = "0") Integer postIndex,
                                             @RequestParam(value = "city", defaultValue = "") String city,
                                             @RequestParam(value = "street", defaultValue = "") String street,
                                             @RequestParam(value = "homeNumber", defaultValue = "0") Integer homeNumber)
    {

        AddressToUser addressToUser = AddressToUser.builder()
                                             .id(id)
                                             .user(this.userService.getUserByID(userId))
                                             .country(country)
                                             .postIndex(postIndex)
                                             .city(city)
                                             .street(street)
                                             .homeNumber(homeNumber)
                                             .build();

        switch(action){

            case "add":

                this.profileInfoService.addProfileInfo(addressToUser);
                break;

            case "update":

                this.profileInfoService.updateProfileInfo(addressToUser);
                break;

            case "delete":

                this.profileInfoService.deleteProfileInfo(id);
                break;

        }

        List<TempAddressToUser> profileInfos = this.profileInfoService.getProfileInfos();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profile_infos", profileInfos);
        modelAndView.setViewName("admin/profile_info_admin");

        return modelAndView;

    }

}
