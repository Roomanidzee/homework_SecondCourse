package com.romanidze.perpenanto.services.implementations.files;

import com.romanidze.perpenanto.domain.user.FileInfo;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.repositories.FileInfoRepository;
import com.romanidze.perpenanto.repositories.ProfileRepository;
import com.romanidze.perpenanto.services.interfaces.files.FileStorageService;
import com.romanidze.perpenanto.services.interfaces.files.PhotoService;
import com.romanidze.perpenanto.utils.FileStorageUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class FileStorageServiceImpl implements FileStorageService{

    private final FileInfoRepository fileInfoRepository;
    private final PhotoService photoService;
    private final ProfileRepository profileRepository;
    private final FileStorageUtil fileStorageUtil;

    @Autowired
    public FileStorageServiceImpl(FileInfoRepository fileInfoRepository, PhotoService photoService,
                                  ProfileRepository profileRepository, FileStorageUtil fileStorageUtil) {
        this.fileInfoRepository = fileInfoRepository;
        this.photoService = photoService;
        this.profileRepository = profileRepository;
        this.fileStorageUtil = fileStorageUtil;
    }

    @Override
    public boolean saveFile(MultipartFile file, Profile profile) {

        FileInfo fileInfo = this.fileStorageUtil.convertFromMultipart(file);

        String type = fileInfo.getType();

        if(type.equals("image/jpeg")){

            Optional<FileInfo> photoImage = this.photoService.getProfilePhoto(profile);

            photoImage.ifPresent(fileInfo2 ->
                                     profile.getFiles()
                                            .removeIf(fileInfo1 -> fileInfo1.equals(fileInfo2)));

            photoImage.ifPresent(this.fileInfoRepository::delete);

        }

        if(profile.getFiles() == null){

            Set<FileInfo> files = new HashSet<>();
            files.add(fileInfo);
            profile.setFiles(files);

        }else{
            profile.getFiles().add(fileInfo);
        }

        this.profileRepository.save(profile);

        this.fileStorageUtil.copyToStorage(file, fileInfo.getStorageFileName());
        return !(fileInfo.getStorageFileName() == null);

    }

    @Override
    @SneakyThrows
    public void writeFileToResponse(String storageFileName, HttpServletResponse response) {

        FileInfo file = this.fileInfoRepository.findByStorageFileName(storageFileName);

        response.setContentType(file.getType());
        InputStream is = new FileInputStream(new File(file.getUrl()));
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();

    }
}
