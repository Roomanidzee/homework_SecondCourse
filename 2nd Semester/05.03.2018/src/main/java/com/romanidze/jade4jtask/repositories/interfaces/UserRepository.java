package com.romanidze.jade4jtask.repositories.interfaces;

import com.romanidze.jade4jtask.domain.User;

/**
 * 05.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserRepository {
    User findById(Long id);
}
