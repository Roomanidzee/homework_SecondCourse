package com.romanidze.perpenanto.converters;

import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component("profileConverter")
public class StringToProfileConverter implements Converter<String, Profile> {

    private final ProfileRepository profileRepository;

    @Autowired
    public StringToProfileConverter(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile convert(String s) {

        if(s.equals("")){
            return null;
        }

        return this.profileRepository.findOne(Long.parseLong(s));
    }
}

