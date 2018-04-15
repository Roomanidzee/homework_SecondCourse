package com.romanidze.perpenanto.services.interfaces.newsletter;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface EmailService {
    void sendMail(String text, String subject, String email);
}
