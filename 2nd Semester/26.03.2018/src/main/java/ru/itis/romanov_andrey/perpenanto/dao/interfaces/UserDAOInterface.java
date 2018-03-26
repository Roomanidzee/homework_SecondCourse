package ru.itis.romanov_andrey.perpenanto.dao.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.User;

public interface UserDAOInterface extends CrudDAOInterface<User, Long> {
    User findByUsername(String username);
}
