package com.romanidze.perpenanto.services.interfaces.newsletter;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface SMSService {
    boolean sendSMS(String phone, String text);
}
