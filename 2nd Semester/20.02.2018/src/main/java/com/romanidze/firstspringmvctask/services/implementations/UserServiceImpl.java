package com.romanidze.firstspringmvctask.services.implementations;

import com.romanidze.firstspringmvctask.domain.User;
import com.romanidze.firstspringmvctask.repositories.interfaces.UserRepository;
import com.romanidze.firstspringmvctask.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 24.02.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService{

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
