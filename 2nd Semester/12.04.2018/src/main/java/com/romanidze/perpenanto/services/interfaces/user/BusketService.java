package com.romanidze.perpenanto.services.interfaces.user;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.Reservation;

import java.util.List;
import java.util.Map;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface BusketService {

    void addProductToBusket(Profile profile, Long productId);
    void removeProductFromBusket(Profile profile, Long productId);
    List<Product> getProductsFromBusket(Profile profile);
    Integer getProductsCount(Profile profile);
    void payForBusket(Profile profile, Reservation reservation);
    Map<Reservation, Integer> getBusketPrice(Profile profile);

}
