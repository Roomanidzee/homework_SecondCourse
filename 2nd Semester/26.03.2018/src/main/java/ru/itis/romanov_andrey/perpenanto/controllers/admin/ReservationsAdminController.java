package ru.itis.romanov_andrey.perpenanto.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.Reservation;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ReservationsServiceInterface;

import java.sql.Timestamp;
import java.util.List;

@Controller("Reservations Admin Controller")
public class ReservationsAdminController {

    @Autowired
    private ReservationsServiceInterface reservationService;

    @RequestMapping(value = "/admin/reservations", method = RequestMethod.GET)
    public ModelAndView getSortedReservations(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        List<Reservation> reservations = this.reservationService.getReservationByCookie(cookieValue);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reservations", reservations);
        modelAndView.setViewName("admin/reservations_admin");

        return modelAndView;

    }

    @RequestMapping(value = "/admin/reservations", params = "form_action", method = RequestMethod.POST)
    public ModelAndView workWithReservations(@RequestParam("form_action") String action,
                                             @RequestParam(value = "id", defaultValue = "0") Long id,
                                             @RequestParam(value = "status", defaultValue = "") String status,
                                             @RequestParam(value = "created_at", defaultValue = "") Timestamp timestamp)
    {

        Reservation reservation = Reservation.builder()
                                             .id(id)
                                             .status(status)
                                             .createdAt(timestamp)
                                             .build();

        switch(action){

            case "add":

                this.reservationService.addReservation(reservation);
                break;

            case "update":

                this.reservationService.updateReservation(reservation);
                break;

            case "delete":

                this.reservationService.deleteReservation(id);
                break;

        }

        List<Reservation> reservations = this.reservationService.getReservations();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reservations", reservations);
        modelAndView.setViewName("admin/reservations_admin");

        return modelAndView;

    }

}
