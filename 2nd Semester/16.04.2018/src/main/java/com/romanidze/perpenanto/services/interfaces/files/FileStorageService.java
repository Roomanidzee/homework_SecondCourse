package com.romanidze.perpenanto.services.interfaces.files;

import com.romanidze.perpenanto.domain.user.Profile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface FileStorageService {

    boolean saveFile(MultipartFile file, Profile profile);
    void writeFileToResponse(String storageFileName, HttpServletResponse response);

}
