package com.romanidze.perpenanto.services.implementations.auth;

import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.security.details.UserDetailsImpl;
import com.romanidze.perpenanto.services.interfaces.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByAuthentication(Authentication authentication) {

        UserDetailsImpl currentUserDetails = (UserDetailsImpl) authentication.getPrincipal();
        User currentUserModel = currentUserDetails.getUser();
        Long currentUserId = currentUserModel.getId();
        return this.userRepository.findOne(currentUserId);

    }
}
