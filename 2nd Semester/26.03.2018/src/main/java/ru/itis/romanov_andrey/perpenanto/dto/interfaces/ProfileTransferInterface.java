package ru.itis.romanov_andrey.perpenanto.dto.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Profile;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempProfile;

import java.util.List;

public interface ProfileTransferInterface {

    List<TempProfile> getTempProfiles(List<Profile> oldList);

}
