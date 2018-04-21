package com.romanidze.perpenanto.dto.interfaces;

import com.romanidze.perpenanto.domain.admin.FileToUser;
import com.romanidze.perpenanto.domain.user.FileInfo;

import java.util.List;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface FileToUserTransfer {

    List<FileToUser> getFilesToUsers(List<FileInfo> files);

}
