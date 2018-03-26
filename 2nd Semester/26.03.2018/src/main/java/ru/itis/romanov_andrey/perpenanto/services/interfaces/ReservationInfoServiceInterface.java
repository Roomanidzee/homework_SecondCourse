package ru.itis.romanov_andrey.perpenanto.services.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.ReservationInfo;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempReservationInfo;

import java.util.List;

public interface ReservationInfoServiceInterface {

    List<TempReservationInfo> getReservationInfos();
    List<TempReservationInfo> getReservationInfosByCookie(String cookieValue);

    void addReservationInfo(ReservationInfo model);
    void updateReservationInfo(ReservationInfo model);
    void deleteReservationInfo(Long id);

}
