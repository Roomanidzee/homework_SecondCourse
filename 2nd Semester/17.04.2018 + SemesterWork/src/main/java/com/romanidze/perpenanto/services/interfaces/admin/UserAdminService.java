package com.romanidze.perpenanto.services.interfaces.admin;

import com.romanidze.perpenanto.forms.admin.UserForm;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserAdminService {

    void addUser(UserForm userForm);
    void updateUser(UserForm userForm);
    void deleteUser(Long id);

}
