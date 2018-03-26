package ru.itis.romanov_andrey.perpenanto.services.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Profile;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempProfile;

import java.util.List;

public interface ProfileServiceInterface {

    List<TempProfile> getProfiles();
    List<TempProfile> getProfilesByCookie(String cookieValue);

    void addProfile(Profile model);
    void updateProfile(Profile model);
    void deleteProfile(Long id);

    Profile getProfileById(Long id);

}
