package com.romanidze.perpenanto.services.interfaces.admin;

import com.romanidze.perpenanto.forms.admin.ProfileForm;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ProfileAdminService {

    void addProfile(ProfileForm profileForm);
    void updateProfile(ProfileForm profileForm);
    void deleteProfile(Long id);

}
