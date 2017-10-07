package com.romanidze.jizzyshop.dao.interfaces;

import com.romanidze.jizzyshop.models.Profile;
import com.romanidze.jizzyshop.models.User;

public interface ProfileDAOInterface extends CrudDAOInterface<Profile, Long>{

    Profile findByUser(User user);
    Profile findByCountry(String country);

}
