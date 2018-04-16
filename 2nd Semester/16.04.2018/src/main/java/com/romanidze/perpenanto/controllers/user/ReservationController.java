package com.romanidze.perpenanto.controllers.user;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.services.interfaces.auth.AuthenticationService;
import com.romanidze.perpenanto.services.interfaces.newsletter.SendPDFService;
import com.romanidze.perpenanto.services.interfaces.user.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.List;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthenticationService authenticationService;
    private final SendPDFService sendPDFService;

    @Autowired
    public ReservationController(ReservationService reservationService, AuthenticationService authenticationService,
                                 SendPDFService sendPDFService) {
        this.reservationService = reservationService;
        this.authenticationService = authenticationService;
        this.sendPDFService = sendPDFService;
    }

    @PostMapping("/user/reservation")
    public ModelAndView getUserReservation(Authentication authentication,
                                           @RequestParam("timestamp")Timestamp timestamp){

        User user = this.authenticationService.getUserByAuthentication(authentication);
        Long id = this.reservationService.getReservationId(timestamp);
        List<Product> products = this.reservationService.getReservationProducts(user.getProfile(), timestamp);
        Integer price = this.reservationService.getReservationPrice(timestamp);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id",id);
        modelAndView.addObject("products",products);
        modelAndView.addObject("reservationPrice", price);
        modelAndView.setViewName("user/reservation");

        this.sendPDFService.sendEmailWithPDF(user, timestamp);

        return modelAndView;

    }

}
