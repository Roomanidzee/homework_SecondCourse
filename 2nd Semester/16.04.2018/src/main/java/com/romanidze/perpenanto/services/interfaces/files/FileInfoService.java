package com.romanidze.perpenanto.services.interfaces.files;

import com.romanidze.perpenanto.domain.admin.FileToUser;
import com.romanidze.perpenanto.domain.user.FileInfo;

import java.util.List;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface FileInfoService {

    List<FileInfo> getFiles();
    List<FileToUser> getFileToUser(List<FileInfo> files);

}
