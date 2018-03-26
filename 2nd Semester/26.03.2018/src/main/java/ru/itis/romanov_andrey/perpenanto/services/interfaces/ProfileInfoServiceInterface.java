package ru.itis.romanov_andrey.perpenanto.services.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.AddressToUser;
import ru.itis.romanov_andrey.perpenanto.models.User;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempAddressToUser;

import java.util.List;

public interface ProfileInfoServiceInterface {

    List<AddressToUser> findAddressByUser(User user);

    List<TempAddressToUser> getProfileInfos();
    List<TempAddressToUser> getProfileInfosByCookie(String cookieValue);

    void addProfileInfo(AddressToUser model);
    void updateProfileInfo(AddressToUser model);
    void deleteProfileInfo(Long id);

}
