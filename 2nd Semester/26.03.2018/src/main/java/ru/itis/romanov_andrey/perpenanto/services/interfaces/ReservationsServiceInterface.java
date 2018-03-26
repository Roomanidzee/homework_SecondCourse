package ru.itis.romanov_andrey.perpenanto.services.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Reservation;

import java.util.List;

public interface ReservationsServiceInterface {

    List<Reservation> getReservations();
    List<Reservation> getReservationByCookie(String cookieValue);

    void addReservation(Reservation model);
    void updateReservation(Reservation model);
    void deleteReservation(Long id);

    Reservation getReservationById(Long id);

}
