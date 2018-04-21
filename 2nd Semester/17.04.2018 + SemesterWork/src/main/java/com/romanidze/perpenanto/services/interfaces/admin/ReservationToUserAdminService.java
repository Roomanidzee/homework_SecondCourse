package com.romanidze.perpenanto.services.interfaces.admin;

import com.romanidze.perpenanto.forms.admin.ReservationToUserForm;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ReservationToUserAdminService {

    void addReservationToUser(ReservationToUserForm reservationToUserForm);
    void updateReservationToUser(ReservationToUserForm reservationToUserForm);
    void deleteReservationToUser(Long orderId);

}
