package ru.itis.romanov_andrey.perpenanto.dao.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.AddressToUser;
import ru.itis.romanov_andrey.perpenanto.models.User;

import java.util.List;

public interface AddressToUserDAOInterface extends CrudDAOInterface<AddressToUser, Long>{

    List<AddressToUser> findByUser(User user);

}
