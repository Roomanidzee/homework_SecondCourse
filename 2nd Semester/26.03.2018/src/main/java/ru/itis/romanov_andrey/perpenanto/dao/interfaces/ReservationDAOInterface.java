package ru.itis.romanov_andrey.perpenanto.dao.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Reservation;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationDAOInterface extends CrudDAOInterface<Reservation, Long>{
    List<Reservation> findAllByStatus(String status);
    List<Reservation> findAllByTimeStamp(Timestamp timestamp);
}
