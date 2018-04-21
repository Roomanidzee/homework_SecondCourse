package com.romanidze.perpenanto.services.interfaces.user;

import com.romanidze.perpenanto.domain.admin.ReservationInfo;
import com.romanidze.perpenanto.domain.admin.ReservationToUser;
import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.Reservation;

import java.sql.Timestamp;
import java.util.List;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ReservationService {

    List<Reservation> getReservations();
    List<Reservation> getReservationsByCookie(String cookieValue);
    List<ReservationInfo> getReservationInfosByCookie(String cookieValue);
    List<ReservationToUser> getReservationToUsersByCookie(String cookieValue);
    void saveOrUpdate(Reservation reservation);
    void delete(Long id);

    Long getReservationId(Timestamp timestamp);
    List<Product> getReservationProducts(Profile profile, Timestamp timestamp);
    Integer getReservationPrice(Timestamp timestamp);
    Reservation getReservationByTimestamp(Timestamp timestamp);

}
