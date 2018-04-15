package com.romanidze.perpenanto.services.interfaces.admin;

import com.romanidze.perpenanto.domain.user.User;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface AdminService {

    void createTempPassword(Long userId);
    void generateHash(User user);

}
