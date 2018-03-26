package ru.itis.romanov_andrey.perpenanto.dto.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.ReservationInfo;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempReservationInfo;

import java.util.List;

public interface ReservationInfoTransferInterface {

    List<TempReservationInfo> getTempReservationInfos(List<ReservationInfo> oldList);

}
