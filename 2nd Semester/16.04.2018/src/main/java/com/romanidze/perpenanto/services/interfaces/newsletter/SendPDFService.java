package com.romanidze.perpenanto.services.interfaces.newsletter;

import com.romanidze.perpenanto.domain.user.FileInfo;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.User;

import java.sql.Timestamp;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface SendPDFService {

    void sendPDF(FileInfo fileInfo, Profile profile);
    void sendEmailWithPDF(User user, Timestamp timestamp);

}
