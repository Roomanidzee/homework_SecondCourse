package com.romanidze.localetask.services.interfaces;

import com.romanidze.localetask.forms.TripRequestForm;

import java.util.List;

/**
 * 23.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface TripRequestService {

    void saveRequest(TripRequestForm tripRequestForm);
    List<TripRequestForm> getAllRequests();

}
