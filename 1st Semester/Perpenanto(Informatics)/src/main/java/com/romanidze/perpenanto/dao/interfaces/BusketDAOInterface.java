package com.romanidze.perpenanto.dao.interfaces;

import com.romanidze.perpenanto.models.Busket;
import com.romanidze.perpenanto.models.Reservation;

public interface BusketDAOInterface extends CrudDAOInterface<Busket, Long>{

    void payForBusket(Busket model, Reservation reservation);

}
