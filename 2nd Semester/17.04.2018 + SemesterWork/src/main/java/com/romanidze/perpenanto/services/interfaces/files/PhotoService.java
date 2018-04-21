package com.romanidze.perpenanto.services.interfaces.files;

import com.romanidze.perpenanto.domain.user.FileInfo;
import com.romanidze.perpenanto.domain.user.Profile;

import java.util.Optional;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface PhotoService {

    Optional<FileInfo> getProfilePhoto(Profile profile);

}
