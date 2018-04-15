package com.romanidze.perpenanto.security.details;

import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = this.userRepository.findByLogin(login)
                                       .orElseThrow(
                                               ()-> new IllegalArgumentException("User not found by login <" + login + ">")
                                       );

        return new UserDetailsImpl(user);

    }
}
