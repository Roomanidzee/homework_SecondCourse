package com.romanidze.perpenanto.services.implementations.auth;

import com.romanidze.perpenanto.domain.user.Address;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.user.UserRegistrationForm;
import com.romanidze.perpenanto.repositories.AddressRepository;
import com.romanidze.perpenanto.repositories.ProfileRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.security.role.Role;
import com.romanidze.perpenanto.security.states.UserState;
import com.romanidze.perpenanto.services.interfaces.auth.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class RegistrationServiceImpl implements RegistrationService{

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, ProfileRepository profileRepository,
                                   AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(UserRegistrationForm userForm) {

        User newUser = User.builder()
                           .login(userForm.getLogin())
                           .protectedPassword(this.passwordEncoder.encode(userForm.getPassword()))
                           .role(Role.USER)
                           .state(UserState.NOT_CONFIRMED)
                           .build();

        Profile profile = Profile.builder()
                                 .personName(userForm.getPersonName())
                                 .personSurname(userForm.getPersonSurname())
                                 .phone(userForm.getPhone())
                                 .email(userForm.getEmail())
                                 .build();

        Address address = Address.builder()
                                 .country(userForm.getCountry())
                                 .postalCode(userForm.getPostalCode())
                                 .city(userForm.getCity())
                                 .street(userForm.getStreet())
                                 .homeNumber(userForm.getHomeNumber())
                                 .build();

        this.userRepository.save(newUser);
        profile.setUser(newUser);
        this.profileRepository.save(profile);
        List<User> users = new ArrayList<>();
        users.add(newUser);
        address.setUsers(users);
        this.addressRepository.save(address);

    }
}
