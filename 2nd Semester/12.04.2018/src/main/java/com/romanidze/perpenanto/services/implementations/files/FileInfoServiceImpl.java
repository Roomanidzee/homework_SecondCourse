package com.romanidze.perpenanto.services.implementations.files;

import com.romanidze.perpenanto.domain.admin.FileToUser;
import com.romanidze.perpenanto.domain.user.FileInfo;
import com.romanidze.perpenanto.dto.interfaces.FileToUserTransfer;
import com.romanidze.perpenanto.repositories.FileInfoRepository;
import com.romanidze.perpenanto.services.interfaces.files.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class FileInfoServiceImpl implements FileInfoService{

    private final FileInfoRepository fileInfoRepository;
    private final FileToUserTransfer fileInfoDTO;

    @Autowired
    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository, FileToUserTransfer fileInfoDTO) {
        this.fileInfoRepository = fileInfoRepository;
        this.fileInfoDTO = fileInfoDTO;
    }

    @Override
    public List<FileInfo> getFiles() {
        return this.fileInfoRepository.findAll();
    }

    @Override
    public List<FileToUser> getFileToUser(List<FileInfo> files) {
        return this.fileInfoDTO.getFilesToUsers(files);
    }
}
