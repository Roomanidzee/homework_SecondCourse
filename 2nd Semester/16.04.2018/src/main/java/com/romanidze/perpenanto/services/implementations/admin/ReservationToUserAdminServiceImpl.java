package com.romanidze.perpenanto.services.implementations.admin;

import com.romanidze.perpenanto.domain.user.Reservation;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.admin.ReservationToUserForm;
import com.romanidze.perpenanto.repositories.ReservationRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.services.interfaces.admin.ReservationToUserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class ReservationToUserAdminServiceImpl implements ReservationToUserAdminService{

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationToUserAdminServiceImpl(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void addReservationToUser(ReservationToUserForm reservationToUserForm) {

        User user = this.userRepository.findOne(reservationToUserForm.getUserId());
        Reservation reservation = this.reservationRepository.findOne(reservationToUserForm.getOrderId());

        if(user.getReservations() == null){

            Set<Reservation> reservations = new HashSet<>();
            reservations.add(reservation);
            user.setReservations(reservations);

        }else{
            user.getReservations().add(reservation);
        }

    }

    @Override
    public void updateReservationToUser(ReservationToUserForm reservationToUserForm) {

        User user = this.userRepository.findOne(reservationToUserForm.getUserId());
        Reservation reservation = this.reservationRepository.findOne(reservationToUserForm.getOrderId());
        user.getReservations().add(reservation);

    }

    @Override
    public void deleteReservationToUser(Long orderId) {

        Reservation reservation = this.reservationRepository.findOne(orderId);
        List<User> users = this.userRepository.findAll();

        users.forEach(user -> user.getReservations().stream()
                                                    .filter(reservation1 -> reservation1.equals(reservation))
                                                    .forEach(user.getReservations()::remove));

    }
}
