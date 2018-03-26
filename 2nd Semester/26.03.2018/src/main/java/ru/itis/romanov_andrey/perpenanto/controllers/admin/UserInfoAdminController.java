package ru.itis.romanov_andrey.perpenanto.controllers.admin;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.ReservationToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempReservationToUser;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ReservationsServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.UserInfoServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.UsersServiceInterface;

import java.util.List;

@Controller("UserInfo Admin Controller")
public class UserInfoAdminController {

    @Autowired
    private UserInfoServiceInterface userInfoService;

    @Autowired
    private UsersServiceInterface userService;

    @Autowired
    private ReservationsServiceInterface reservationService;

    @RequestMapping(value = "/admin/user_infos", method = RequestMethod.GET)
    public ModelAndView getSortedUserInfos(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        List<TempReservationToUser> userInfos = this.userInfoService.getUserInfoByCookie(cookieValue);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user_infos", userInfos);
        modelAndView.setViewName("admin/user_info_admin");

        return modelAndView;

    }

    @RequestMapping(value = "/admin/user_infos", params = "form_action", method = RequestMethod.POST)
    public ModelAndView workWithUserInfos(@RequestParam("form_action") String action,
                                          @RequestParam(value = "id", defaultValue = "0") Long id,
                                          @RequestParam(value = "user_id", defaultValue = "0") Long userId,
                                          @RequestParam(value = "order_id", defaultValue = "0") Long reservationId)
    {

        ReservationToUser reservationToUser = ReservationToUser.builder()
                                    .id(id)
                                    .user(this.userService.getUserByID(userId))
                                    .userReservations(Lists.newArrayList())
                                    .build();

        reservationToUser.getUserReservations().add(this.reservationService.getReservationById(reservationId));

        switch(action){

            case "add":
                this.userInfoService.addUserInfo(reservationToUser);
                break;
            case "update":
                this.userInfoService.updateUserInfo(reservationToUser);
                break;
            case "delete":
                this.userInfoService.deleteUserInfo(id);
                break;

        }

        List<TempReservationToUser> userInfos = this.userInfoService.getUserInfos();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user_infos", userInfos);
        modelAndView.setViewName("admin/user_info_admin");

        return modelAndView;

    }

}
