package com.romanidze.perpenanto.services.implementations.files;

import com.romanidze.perpenanto.domain.user.FileInfo;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.services.interfaces.files.PhotoService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class PhotoServiceImpl implements PhotoService{

    @Override
    public Optional<FileInfo> getProfilePhoto(Profile profile) {

        Set<FileInfo> profileFiles = profile.getFiles();

        return profileFiles.stream()
                           .filter(fileInfo -> fileInfo.getType().equals("image/jpeg"))
                           .findFirst();

    }
}
