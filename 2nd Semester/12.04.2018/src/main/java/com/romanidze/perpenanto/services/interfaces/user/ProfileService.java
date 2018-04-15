package com.romanidze.perpenanto.services.interfaces.user;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.Reservation;
import com.romanidze.perpenanto.domain.user.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ProfileService {

    List<Profile> getProfiles();
    List<Profile> getProfilesByCookie(String cookieValue);
    void saveOrUpdate(Profile profile);
    void delete(Long id);

    Profile findByUserId(Long userId);

    int countReservations(User user);
    Set<Product> getProductsByUser(User user);
    int getCommonProductsPrice(User user);
    int getSpendedMoneyOnReservations(User user);
    int getSoldedProductsCount(User user);
    Map<Reservation, Integer> getReservationInformation(User user);

}
