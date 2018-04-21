package com.romanidze.perpenanto.services.interfaces.admin;

import com.romanidze.perpenanto.forms.admin.ReservationInfoForm;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ReservationInfoAdminService {

    void addReservationInfo(ReservationInfoForm reservationInfoForm);
    void updateReservationInfo(ReservationInfoForm reservationInfoForm);
    void deleteReservationInfo(Long productId);

}
