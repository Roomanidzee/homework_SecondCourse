package com.romanidze.formvalidatetask.services.interfaces;

import com.romanidze.formvalidatetask.forms.TripRequestForm;

import java.util.List;

/**
 * 09.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface TripRequestService {

    void saveRequest(TripRequestForm tripRequestForm);
    List<TripRequestForm> getAllRequests();

}
