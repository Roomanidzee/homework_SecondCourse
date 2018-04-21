package com.romanidze.perpenanto.services.implementations.admin;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Reservation;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.admin.ReservationInfoForm;
import com.romanidze.perpenanto.repositories.ProductRepository;
import com.romanidze.perpenanto.repositories.ReservationRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.services.interfaces.admin.ReservationInfoAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class ReservationInfoAdminServiceImpl implements ReservationInfoAdminService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReservationInfoAdminServiceImpl(UserRepository userRepository, ReservationRepository reservationRepository,
                                           ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addReservationInfo(ReservationInfoForm reservationInfoForm) {

        User user = this.userRepository.findOne(reservationInfoForm.getUserId());
        Reservation reservation = this.reservationRepository.findOne(reservationInfoForm.getOrderId());
        Product product = this.productRepository.findOne(reservationInfoForm.getProductId());

        Reservation changeableReservation = user.getReservations()
                                                .stream()
                                                .filter(reservation1 -> reservation1.equals(reservation))
                                                .findFirst()
                                                .orElseThrow(() -> new NullPointerException("No such Reservation!"));

        changeableReservation.getProducts().add(product);
        this.reservationRepository.save(changeableReservation);

    }

    @Override
    public void updateReservationInfo(ReservationInfoForm reservationInfoForm) {

        User user = this.userRepository.findOne(reservationInfoForm.getUserId());
        Reservation reservation = this.reservationRepository.findOne(reservationInfoForm.getOrderId());
        Product product = this.productRepository.findOne(reservationInfoForm.getProductId());

        user.getReservations().stream()
                              .filter(reservation1 -> reservation1.equals(reservation))
                              .forEach(reservation1 -> reservation1.getProducts().add(product));

        this.userRepository.save(user);

    }

    @Override
    public void deleteReservationInfo(Long productId) {

        Product product = this.productRepository.findOne(productId);
        List<User> users = this.userRepository.findAll();

        users.stream()
             .map(User::getReservations)
             .forEachOrdered(reservations -> {

                       List<Product> deleteProducts =
                                    reservations.stream()
                                                .flatMap(reservation1 -> reservation1.getProducts().stream())
                                                .filter(product1 -> product1.equals(product))
                                                .collect(Collectors.toList());

                         deleteProducts.forEach(product1 ->
                                 reservations.stream()
                                             .filter(reservation -> reservation.getProducts().contains(product1))
                                             .forEach(reservation -> reservation.getProducts().remove(product1)));
                 }
             );

        this.userRepository.save(users);

    }
}
