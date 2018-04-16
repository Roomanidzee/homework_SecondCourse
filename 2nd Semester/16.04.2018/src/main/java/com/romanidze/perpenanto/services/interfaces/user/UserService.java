package com.romanidze.perpenanto.services.interfaces.user;

import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.user.AddProductForm;
import com.romanidze.perpenanto.security.role.Role;

import java.util.List;
import java.util.Optional;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserService {

    List<User> getUsers();
    List<User> getUsersByRole(Role role);
    List<User> getUsersByRoleAndCookie(Role role, String cookieValue);
    void saveOrUpdate(User user);
    void delete(Long id);
    Optional<User> findByConfirmHash(String confirmHash);
    Optional<User> findByLogin(String login);
    Optional<User> findById(Long id);
    void addProduct(User user, AddProductForm addProductForm);

}
