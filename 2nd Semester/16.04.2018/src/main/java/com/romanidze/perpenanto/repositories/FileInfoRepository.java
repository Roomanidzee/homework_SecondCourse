package com.romanidze.perpenanto.repositories;

import com.romanidze.perpenanto.domain.user.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

    FileInfo findByStorageFileName(String storageFileName);

}
