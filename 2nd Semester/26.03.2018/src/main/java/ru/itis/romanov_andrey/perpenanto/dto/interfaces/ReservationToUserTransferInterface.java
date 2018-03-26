package ru.itis.romanov_andrey.perpenanto.dto.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.temp.TempReservationToUser;
import ru.itis.romanov_andrey.perpenanto.models.ReservationToUser;

import java.util.List;

public interface ReservationToUserTransferInterface {

    List<TempReservationToUser> getTempReservationToUsers(List<ReservationToUser> oldList);

}
