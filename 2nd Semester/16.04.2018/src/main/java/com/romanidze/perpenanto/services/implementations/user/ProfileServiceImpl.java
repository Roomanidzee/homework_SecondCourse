package com.romanidze.perpenanto.services.implementations.user;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.Reservation;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.repositories.ProfileRepository;
import com.romanidze.perpenanto.repositories.ReservationRepository;
import com.romanidze.perpenanto.services.interfaces.user.ProfileService;
import com.romanidze.perpenanto.utils.CompareAttributes;
import com.romanidze.perpenanto.utils.StreamCompareAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, ReservationRepository reservationRepository) {
        this.profileRepository = profileRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Profile> getProfiles() {
        return this.profileRepository.findAll();
    }

    @Override
    public List<Profile> getProfilesByCookie(String cookieValue) {

        List<Profile> currentProfiles = this.profileRepository.findAll();
        int size = 3;

        Function<Profile, String> zero = (Profile p) -> String.valueOf(p.getId());
        Function<Profile, String> first = Profile::getPersonName;
        Function<Profile, String> second = Profile::getPersonSurname;

        List<Function<Profile, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<Profile, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<Profile> compareAttr = new StreamCompareAttributes<>();

        return new ArrayList<>(compareAttr.sortList(currentProfiles, functionMap, cookieValue));

    }

    @Override
    public void saveOrUpdate(Profile profile) {
        this.profileRepository.save(profile);
    }

    @Override
    public void delete(Long id) {
        this.profileRepository.delete(id);
    }

    @Override
    public Profile findByUserId(Long userId) {
        return this.profileRepository.findByUserId(userId);
    }

    @Override
    public int countReservations(User user) {
        return user.getReservations().size();
    }

    @Override
    public Set<Product> getProductsByUser(User user) {
        return user.getProducts();
    }

    @Override
    public int getCommonProductsPrice(User user) {

        return  user.getProducts().stream()
                                  .mapToInt(Product::getPrice)
                                  .sum();

    }

    @Override
    public int getSpendedMoneyOnReservations(User user) {

        return user.getReservations().stream()
                                     .mapToInt(reservation -> reservation.getProducts()
                                                                         .stream()
                                                                         .mapToInt(Product::getPrice)
                                                                         .sum()
                                     )
                                     .sum();
    }

    @Override
    public int getSoldedProductsCount(User user) {

        List<Reservation> reservations = this.reservationRepository.findAll();
        Set<Product> products = user.getProducts();

        return reservations.stream()
                .map(Reservation::getProducts)
                .mapToInt(products1 -> products1.stream()
                                                .mapToInt(
                                                        product1 ->
                                                                (int) products.stream()
                                                                              .filter(product -> product.equals(product1))
                                                                              .count()
                                                )
                                                .sum()
                )
                .sum();
    }

    @Override
    public Map<Reservation, Integer> getReservationInformation(User user) {

        Map<Reservation, Integer> resultMap = new HashMap<>();

        Set<Reservation> reservations = user.getReservations();
        List<Reservation> reservationList = new ArrayList<>(reservations);

        reservationList.forEach(reservation -> {
            List<Product> products = reservation.getProducts();
            int price = products.stream()
                                .mapToInt(Product::getPrice)
                                .sum();
            resultMap.put(reservation, price);
        });

        return resultMap;

    }
}
