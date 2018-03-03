package com.romanidze.viewresolvertask.services.implementations;

import com.romanidze.viewresolvertask.domain.User;
import com.romanidze.viewresolvertask.repositories.interfaces.UserRepository;
import com.romanidze.viewresolvertask.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 03.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id);
    }
}
