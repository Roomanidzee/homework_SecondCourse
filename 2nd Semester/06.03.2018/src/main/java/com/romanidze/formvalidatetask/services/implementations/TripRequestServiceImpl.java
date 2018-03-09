package com.romanidze.formvalidatetask.services.implementations;

import com.romanidze.formvalidatetask.domain.TripRequest;
import com.romanidze.formvalidatetask.forms.TripRequestForm;
import com.romanidze.formvalidatetask.repositories.interfaces.TripRequestRepository;
import com.romanidze.formvalidatetask.services.interfaces.TripRequestService;
import com.romanidze.formvalidatetask.utils.ConstantsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 09.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class TripRequestServiceImpl implements TripRequestService{

    private final TripRequestRepository tripRequestRepository;
    private final ConstantsClass constants;

    @Autowired
    public TripRequestServiceImpl(TripRequestRepository tripRequestRepository, ConstantsClass constants) {
       this.tripRequestRepository = tripRequestRepository;
       this.constants = constants;
    }

    @Override
    public void saveRequest(TripRequestForm tripRequestForm) {
        this.tripRequestRepository.saveTripRequest(tripRequestForm);
    }

    @Override
    public List<TripRequestForm> getAllRequests() {

       List<TripRequest> originalList = this.tripRequestRepository.findAll();

        return originalList.stream()
                           .map(tripRequest ->
                                   TripRequestForm.builder()
                                                  .country(tripRequest.getCountry())
                                                  .departureDate(tripRequest.getDepartureDate()
                                                                            .format(this.constants.getFormatter()))
                                                  .arrivalDate(tripRequest.getArrivalDate()
                                                                          .format(this.constants.getFormatter()))
                                                  .build()
                           )
                           .collect(Collectors.toList());

    }
}
