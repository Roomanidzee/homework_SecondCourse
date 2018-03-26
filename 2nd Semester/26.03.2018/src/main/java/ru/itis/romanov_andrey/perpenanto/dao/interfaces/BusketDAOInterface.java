package ru.itis.romanov_andrey.perpenanto.dao.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Busket;
import ru.itis.romanov_andrey.perpenanto.models.Profile;

import java.util.List;

public interface BusketDAOInterface extends CrudDAOInterface<Busket, Long>{
    List<Busket> findAllByUserProfile(Profile profile);
    void deleteByUserProfile(Long userProfileId);
}
