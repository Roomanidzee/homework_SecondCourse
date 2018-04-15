package com.romanidze.perpenanto.controllers.user;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Reservation;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.services.interfaces.auth.AuthenticationService;
import com.romanidze.perpenanto.services.interfaces.user.BusketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("Busket Controller")
public class BusketController {

    private final BusketService busketService;
    private final AuthenticationService authenticationService;

    @Autowired
    public BusketController(BusketService busketService, AuthenticationService authenticationService) {
        this.busketService = busketService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/user/busket")
    public ModelAndView getProductsFromBusket(Authentication authentication){

        User user = this.authenticationService.getUserByAuthentication(authentication);
        List<Product> products = this.busketService.getProductsFromBusket(user.getProfile());

        Map<Reservation, Integer> orderMap = new HashMap<>(this.busketService.getBusketPrice(user.getProfile()));
        Map.Entry<Reservation, Integer> entry = orderMap.entrySet().iterator().next();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("reservationPrice", entry.getValue());
        modelAndView.addObject("timestamp", entry.getKey().getCreatedAt());
        modelAndView.setViewName("user/busket");

        return modelAndView;

    }

}
