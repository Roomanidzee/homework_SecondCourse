package com.romanidze.perpenanto.services.interfaces;

import com.romanidze.perpenanto.models.temp.TempReservationToUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ReservationToUserServiceInterface {

    List<TempReservationToUser> getReservationToUserByCookie(HttpServletRequest req, HttpServletResponse resp);

}
