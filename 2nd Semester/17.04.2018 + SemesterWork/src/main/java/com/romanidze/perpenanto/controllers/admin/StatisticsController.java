package com.romanidze.perpenanto.controllers.admin;

import com.romanidze.perpenanto.security.role.Role;
import com.romanidze.perpenanto.services.interfaces.files.FileInfoService;
import com.romanidze.perpenanto.services.interfaces.user.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("Admin Statistics Controller")
@RequestMapping("/admin/statistics")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsController {

    private AddressService addressService;
    private UserService userService;
    private ProductService productService;
    private ProfileService profileService;
    private ReservationService reservationService;
    private FileInfoService fileInfoService;

    @GetMapping(value = "/security")
    public ModelAndView getUsers(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/users_admin");
        modelAndView.addObject("users", this.userService.getUsersByRoleAndCookie(Role.USER, cookieValue));

        return modelAndView;

    }

    @GetMapping(value = "/products")
    public ModelAndView getProducts(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/products_admin");
        modelAndView.addObject("products", this.productService.getProductsByCookie(cookieValue));

        return modelAndView;

    }

    @GetMapping(value = "/profiles")
    public ModelAndView getProfiles(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/profiles_admin");
        modelAndView.addObject("profiles", this.profileService.getProfilesByCookie(cookieValue));

        return modelAndView;

    }

    @GetMapping(value = "/reservations")
    public ModelAndView getReservations(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/reservations_admin");
        modelAndView.addObject("reservations", this.reservationService.getReservationsByCookie(cookieValue));

        return modelAndView;

    }

    @GetMapping(value = "/addresses")
    public ModelAndView getAddresses(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/address_to_user_admin");
        modelAndView.addObject("address_to_users",
                this.addressService.getAddressToUsersByCookie(cookieValue));

        return modelAndView;

    }

    @GetMapping(value = "/reservations_to_user")
    public ModelAndView getReservationsToUser(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/reservation_to_user_admin");
        modelAndView.addObject("reservations_to_users",
                this.reservationService.getReservationToUsersByCookie(cookieValue));

        return modelAndView;

    }

    @GetMapping(value = "/reservations_infos")
    public ModelAndView getReservationInfos(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/reservation_info_admin");
        modelAndView.addObject("reservation_infos",
                this.reservationService.getReservationInfosByCookie(cookieValue));

        return modelAndView;

    }

    @GetMapping(value = "/products_to_user")
    public ModelAndView getProductsToUser(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/product_to_user_admin");
        modelAndView.addObject("products_to_users",
                this.productService.getProductsToUserByCookie(cookieValue));

        return modelAndView;

    }

    @GetMapping("/files")
    public ModelAndView getFilesToUser(){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/file_to_user_admin");
        modelAndView.addObject("files_to_user",
                this.fileInfoService.getFileToUser(this.fileInfoService.getFiles()));

        return modelAndView;

    }
}
