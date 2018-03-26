package ru.itis.romanov_andrey.perpenanto.controllers.admin;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.ReservationInfo;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempReservationInfo;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProductServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProfileServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ReservationInfoServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ReservationsServiceInterface;

import java.util.List;

@Controller("ReservationInfo Admin Controller")
public class ReservationsInfoAdminController {

    @Autowired
    private ReservationInfoServiceInterface reservationInfoService;

    @Autowired
    private ProfileServiceInterface profileService;

    @Autowired
    private ReservationsServiceInterface reservationService;

    @Autowired
    private ProductServiceInterface productService;

    @RequestMapping(value = "/admin/reservations_infos", method = RequestMethod.GET)
    public ModelAndView getSortedReservationInfos(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        List<TempReservationInfo> reservationInfos = this.reservationInfoService.getReservationInfosByCookie(cookieValue);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reservation_infos", reservationInfos);
        modelAndView.setViewName("admin/reservation_info_admin");

        return modelAndView;

    }

    @RequestMapping(value = "/admin/reservations_infos", params = "form_action", method = RequestMethod.POST)
    public ModelAndView workWithReservationInfos(@RequestParam("form_action") String action,
                                                 @RequestParam(value = "id", defaultValue = "0") Long id,
                                                 @RequestParam(value = "user_id", defaultValue = "0") Long userId,
                                                 @RequestParam(value = "order_id", defaultValue = "0") Long reservationId,
                                                 @RequestParam(value = "product_id", defaultValue = "0") Long productId)
    {

        ReservationInfo reservationInfo = ReservationInfo.builder()
                                                         .id(id)
                                                         .userProfile(this.profileService.getProfileById(userId))
                                                         .userReservation(this.reservationService.getReservationById(reservationId))
                                                         .reservationProducts(Lists.newArrayList())
                                                         .build();

        reservationInfo.getReservationProducts().add(this.productService.getProductById(productId));

        switch(action){

            case "add":

                this.reservationInfoService.addReservationInfo(reservationInfo);
                break;

            case "update":

                this.reservationInfoService.updateReservationInfo(reservationInfo);
                break;

            case "delete":

                this.reservationInfoService.deleteReservationInfo(id);
                break;

        }

        List<TempReservationInfo> reservationInfos = this.reservationInfoService.getReservationInfos();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reservation_infos", reservationInfos);
        modelAndView.setViewName("admin/reservation_info_admin");

        return modelAndView;

    }

}
