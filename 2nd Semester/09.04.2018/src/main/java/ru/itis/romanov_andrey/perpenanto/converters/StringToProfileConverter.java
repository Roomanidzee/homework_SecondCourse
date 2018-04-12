package ru.itis.romanov_andrey.perpenanto.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProfileDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Profile;

/**
 * 06.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component("profileConverter")
public class StringToProfileConverter implements Converter<String, Profile>{

    private final ProfileDAOInterface profileDAOInterface;

    @Autowired
    public StringToProfileConverter(ProfileDAOInterface profileDAOInterface) {
        this.profileDAOInterface = profileDAOInterface;
    }

    @Override
    public Profile convert(String s) {

        if(s.equals("")){
            return null;
        }

        return this.profileDAOInterface.find(Long.parseLong(s));
    }
}
