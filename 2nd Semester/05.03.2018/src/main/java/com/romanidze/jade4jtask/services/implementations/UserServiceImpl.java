package com.romanidze.jade4jtask.services.implementations;

import com.romanidze.jade4jtask.domain.User;
import com.romanidze.jade4jtask.repositories.interfaces.UserRepository;
import com.romanidze.jade4jtask.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 05.03.2018
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
