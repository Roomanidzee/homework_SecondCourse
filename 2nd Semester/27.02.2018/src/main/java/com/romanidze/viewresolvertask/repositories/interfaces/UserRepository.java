package com.romanidze.viewresolvertask.repositories.interfaces;

import com.romanidze.viewresolvertask.domain.User;

/**
 * 03.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserRepository {
    User findById(Long id);
}
