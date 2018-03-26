package ru.itis.romanov_andrey.perpenanto.dao.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Profile;
import ru.itis.romanov_andrey.perpenanto.models.Reservation;
import ru.itis.romanov_andrey.perpenanto.models.ReservationInfo;

import java.util.List;

public interface ReservationInfoDAOInterface extends CrudDAOInterface<ReservationInfo, Long>{
    List<ReservationInfo> findAllByUserProfile(Profile profile);
    ReservationInfo findByUserReservation(Reservation reservation);
    void deleteAllByUserProfile(Long userProfileId);
}
