package com.romanidze.perpenanto.services.interfaces.auth;

import com.romanidze.perpenanto.domain.user.User;
import org.springframework.security.core.Authentication;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface AuthenticationService {
    User getUserByAuthentication(Authentication authentication);
}
