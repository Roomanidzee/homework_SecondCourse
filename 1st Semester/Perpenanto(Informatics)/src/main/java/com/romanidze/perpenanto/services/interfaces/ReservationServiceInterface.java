package com.romanidze.perpenanto.services.interfaces;

import com.romanidze.perpenanto.models.Reservation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ReservationServiceInterface {

    List<Reservation> getReservationByCookie(HttpServletRequest req, HttpServletResponse resp);

}
