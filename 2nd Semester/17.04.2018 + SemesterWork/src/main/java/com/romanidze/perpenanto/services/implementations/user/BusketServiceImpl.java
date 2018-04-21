package com.romanidze.perpenanto.services.implementations.user;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.Reservation;
import com.romanidze.perpenanto.repositories.ProductRepository;
import com.romanidze.perpenanto.repositories.ProfileRepository;
import com.romanidze.perpenanto.repositories.ReservationRepository;
import com.romanidze.perpenanto.security.states.ReservationState;
import com.romanidze.perpenanto.services.interfaces.user.BusketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class BusketServiceImpl implements BusketService{

    private final ProfileRepository profileRepository;
    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public BusketServiceImpl(ProfileRepository profileRepository, ProductRepository productRepository,
                             ReservationRepository reservationRepository) {
        this.profileRepository = profileRepository;
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void addProductToBusket(Profile profile, Long productId) {

        Product product = this.productRepository.findOne(productId);

        if(profile.getProducts() == null){

            List<Product> products = new ArrayList<>();
            products.add(product);
            profile.setProducts(Collections.singletonList(product));

        }else{
            profile.getProducts().add(product);
        }

        this.profileRepository.save(profile);

    }

    @Override
    public void removeProductFromBusket(Profile profile, Long productId) {

        Product product = this.productRepository.findOne(productId);
        profile.getProducts().removeIf(product1 -> product1.equals(product));
        this.profileRepository.save(profile);

    }

    @Override
    public List<Product> getProductsFromBusket(Profile profile) {
        return profile.getProducts();
    }

    @Override
    public Integer getProductsCount(Profile profile) {
        return profile.getProducts().size();
    }

    @Override
    public void payForBusket(Profile profile, Reservation reservation) {

        List<Product> products = profile.getProducts();

        IntStream.range(0, products.size())
                 .forEachOrdered(i -> {

                     if (products.get(i).getReservations() != null) {
                         products.get(i).getReservations().add(reservation);
                     } else {
                         List<Reservation> reservations = new ArrayList<>();
                         reservations.add(reservation);
                         products.get(i).setReservations(reservations);
                     }

                 });

        profile.getProducts().addAll(products);
        profile.getUser().getReservations().add(reservation);

        IntStream.range(0, products.size())
                 .forEachOrdered(i -> profile.getProducts()
                                             .removeIf(product -> product.equals(products.get(i))));

        this.reservationRepository.save(reservation);
        this.profileRepository.save(profile);

    }

    @Override
    public Map<Reservation, Integer> getBusketPrice(Profile profile) {

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));

        Reservation reservation = Reservation.builder()
                                             .createdAt(Timestamp.valueOf(localDateTime))
                                             .status(ReservationState.COLLECTING)
                                             .build();

        this.reservationRepository.save(reservation);

        if(profile.getUser().getReservations() == null){

            Set<Reservation> reservations = new HashSet<>();
            reservations.add(reservation);
            profile.getUser().setReservations(reservations);

        }else{
            profile.getUser().getReservations().add(reservation);
        }

        int price = profile.getProducts()
                           .stream()
                           .mapToInt(Product::getPrice)
                           .sum();

        Map<Reservation, Integer> resultMap = new HashMap<>();
        resultMap.put(reservation, price);
        return resultMap;

    }
}
