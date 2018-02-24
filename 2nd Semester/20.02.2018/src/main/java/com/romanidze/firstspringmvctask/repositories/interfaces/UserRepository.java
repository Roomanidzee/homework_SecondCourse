package com.romanidze.firstspringmvctask.repositories.interfaces;

import com.romanidze.firstspringmvctask.domain.User;

/**
 * 24.02.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserRepository {
    User findById(Long id);
}
