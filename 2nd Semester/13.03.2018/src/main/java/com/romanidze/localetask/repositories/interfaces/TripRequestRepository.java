package com.romanidze.localetask.repositories.interfaces;

import com.romanidze.localetask.domain.TripRequest;
import com.romanidze.localetask.forms.TripRequestForm;

import java.util.List;

/**
 * 23.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface TripRequestRepository {

    void saveTripRequest(TripRequestForm tripRequestForm);
    List<TripRequest> findAll();

}
