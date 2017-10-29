package com.romanidze.perpenanto.services.interfaces;

import com.romanidze.perpenanto.models.temp.TempReservationInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ReservationInfoServiceInterface {

    List<TempReservationInfo> getReservationInfosByCookie(HttpServletRequest req, HttpServletResponse resp);

}
