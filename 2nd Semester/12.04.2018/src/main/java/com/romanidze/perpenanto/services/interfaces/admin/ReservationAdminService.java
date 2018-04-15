package com.romanidze.perpenanto.services.interfaces.admin;

import com.romanidze.perpenanto.forms.admin.ReservationForm;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ReservationAdminService {

    void addReservation(ReservationForm reservationForm);
    void updateReservation(ReservationForm reservationForm);
    void deleteReservation(Long id);

}
