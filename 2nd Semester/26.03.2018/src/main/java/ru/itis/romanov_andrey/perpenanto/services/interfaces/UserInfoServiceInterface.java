package ru.itis.romanov_andrey.perpenanto.services.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.temp.TempReservationToUser;
import ru.itis.romanov_andrey.perpenanto.models.ReservationToUser;

import java.util.List;

public interface UserInfoServiceInterface {

    List<TempReservationToUser> getUserInfos();
    List<TempReservationToUser> getUserInfoByCookie(String cookieValue);

    void addUserInfo(ReservationToUser model);
    void updateUserInfo(ReservationToUser model);
    void deleteUserInfo(Long id);

}
