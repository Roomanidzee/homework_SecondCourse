package ru.itis.romanov_andrey.perpenanto.dao.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.User;
import ru.itis.romanov_andrey.perpenanto.models.ReservationToUser;

import java.util.List;

public interface ReservationToUserDAOInterface extends CrudDAOInterface<ReservationToUser, Long>{
    List<ReservationToUser> findAllByUser(User user);
    void deleteAllByUser(Long userId);
}
