package com.romanidze.perpenanto.dto.implementations;

import com.romanidze.perpenanto.domain.admin.FileToUser;
import com.romanidze.perpenanto.domain.user.FileInfo;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.dto.interfaces.FileToUserTransfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class FileToUserTransferImpl implements FileToUserTransfer {

    @Override
    public List<FileToUser> getFilesToUsers(List<FileInfo> files) {

        List<FileToUser> resultList = new ArrayList<>();

        IntStream.range(0, files.size())
                .forEachOrdered(i -> {

                    FileInfo file = files.get(i);
                    List<Profile> profiles = files.get(i).getProfiles();
                    profiles.stream()
                            .map(profile -> FileToUser.builder()
                                                      .fileId(file.getId())
                                                      .userId(profile.getUser().getId())
                                                      .build()
                            )
                            .forEachOrdered(resultList::add);

                });

        return resultList;

    }
}
