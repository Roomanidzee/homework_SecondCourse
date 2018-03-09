package com.romanidze.formvalidatetask.repositories.interfaces;

import com.romanidze.formvalidatetask.domain.TripRequest;
import com.romanidze.formvalidatetask.forms.TripRequestForm;

import java.util.List;

/**
 * 09.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface TripRequestRepository {

    void saveTripRequest(TripRequestForm tripRequestForm);
    List<TripRequest> findAll();

}
