package com.romanidze.jade4jtask.services.interfaces;

import com.romanidze.jade4jtask.domain.User;

/**
 * 05.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserService {
    User getUserById(Long id);
}
