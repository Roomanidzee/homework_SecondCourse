package com.romanidze.perpenanto.services.implementations.admin;

import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.admin.UserForm;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.security.role.Role;
import com.romanidze.perpenanto.security.states.UserState;
import com.romanidze.perpenanto.services.interfaces.admin.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class UserAdminServiceImpl implements UserAdminService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private UserState[] userStatesTemp = UserState.values();
    private Role[] rolesTemp = Role.values();

    @Autowired
    public UserAdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(UserForm userForm) {

        UserState userState = Arrays.stream(this.userStatesTemp)
                                    .filter(userState1 -> userForm.getState()
                                                                  .toUpperCase()
                                                                  .equals(userState1.toString()))
                                    .findFirst()
                                    .orElseThrow(() -> new NullPointerException("No such User state!"));

        Role role = Arrays.stream(this.rolesTemp)
                          .filter(role1 -> userForm.getRole()
                                                   .toUpperCase()
                                                   .equals(role1.toString()))
                          .findFirst()
                          .orElseThrow(() -> new NullPointerException("No such User state!"));

        User user = User.builder()
                        .login(userForm.getLogin())
                        .protectedPassword(this.passwordEncoder.encode(userForm.getPassword()))
                        .role(role)
                        .state(userState)
                        .build();

        this.userRepository.save(user);

    }

    @Override
    public void updateUser(UserForm userForm) {

        UserState userState = Arrays.stream(this.userStatesTemp)
                                    .filter(userState1 -> userForm.getState()
                                                                  .toUpperCase()
                                                                  .equals(userState1.toString()))
                                    .findFirst()
                                    .orElseThrow(() -> new NullPointerException("No such User state!"));

        Role role = Arrays.stream(this.rolesTemp)
                          .filter(role1 -> userForm.getRole()
                                                   .toUpperCase()
                                                   .equals(role1.toString()))
                          .findFirst()
                          .orElseThrow(() -> new NullPointerException("No such User state!"));

        User user1 = this.userRepository.findOne(userForm.getId());

        user1.setLogin(userForm.getLogin());
        user1.setProtectedPassword(this.passwordEncoder.encode(userForm.getPassword()));
        user1.setRole(role);
        user1.setState(userState);
        this.userRepository.save(user1);

    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.delete(id);
    }
}
