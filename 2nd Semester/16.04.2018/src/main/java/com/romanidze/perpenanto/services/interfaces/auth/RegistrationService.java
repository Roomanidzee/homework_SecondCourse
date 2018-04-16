package com.romanidze.perpenanto.services.interfaces.auth;

import com.romanidze.perpenanto.forms.user.UserRegistrationForm;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface RegistrationService {
    void register(UserRegistrationForm userForm);
}
