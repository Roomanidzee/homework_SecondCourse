package com.romanidze.perpenanto.services.implementations.admin;

import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.admin.ProfileForm;
import com.romanidze.perpenanto.repositories.ProfileRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.services.interfaces.admin.ProfileAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class ProfileAdminServiceImpl implements ProfileAdminService{

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProfileAdminServiceImpl(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addProfile(ProfileForm profileForm) {

        User user = this.userRepository.findOne(profileForm.getUserId());

        Profile profile = Profile.builder()
                                 .personName(profileForm.getPersonName())
                                 .personSurname(profileForm.getPersonSurname())
                                 .email(profileForm.getEmail())
                                 .user(user)
                                 .build();

        this.profileRepository.save(profile);

    }

    @Override
    public void updateProfile(ProfileForm profileForm) {

        Profile profile = this.profileRepository.findOne(profileForm.getId());
        User user = this.userRepository.findOne(profileForm.getUserId());

        if(profile == null){
            throw new NullPointerException("Profile not found!");
        }

        profile.setPersonName(profileForm.getPersonName());
        profile.setPersonSurname(profileForm.getPersonSurname());
        profile.setEmail(profileForm.getEmail());
        profile.setUser(user);

        this.profileRepository.save(profile);

    }

    @Override
    public void deleteProfile(Long id) {
        this.profileRepository.delete(id);
    }
}
