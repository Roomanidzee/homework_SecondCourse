package ru.itis.romanov_andrey.perpenanto.dao.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Profile;
import ru.itis.romanov_andrey.perpenanto.models.User;

public interface ProfileDAOInterface extends CrudDAOInterface<Profile, Long>{
    Profile findByUser(User user);
}
