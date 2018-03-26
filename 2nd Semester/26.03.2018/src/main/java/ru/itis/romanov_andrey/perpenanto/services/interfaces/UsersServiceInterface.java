package ru.itis.romanov_andrey.perpenanto.services.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.User;

import java.util.List;

public interface UsersServiceInterface {

    User getUserByID(Long id);

    List<User> getUsers();
    List<User> getUserByCookie(String cookieValue);

    void addUser(User model);
    void updateUser(User model);
    void deleteUser(Long id);

}
