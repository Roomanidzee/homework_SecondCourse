package com.romanidze.perpenanto.services.implementations;

import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.models.Profile;
import com.romanidze.perpenanto.services.interfaces.ProfileServiceInterface;

public class ProfileServiceImpl implements ProfileServiceInterface{

    private ProfileDAOInterface profileDAO;

    private ProfileServiceImpl(){}

    public ProfileServiceImpl(ProfileDAOInterface profileDAO){

        this.profileDAO = profileDAO;

    }

    @Override
    public void addProfile(Profile model) {
        this.profileDAO.save(model);
    }
}
