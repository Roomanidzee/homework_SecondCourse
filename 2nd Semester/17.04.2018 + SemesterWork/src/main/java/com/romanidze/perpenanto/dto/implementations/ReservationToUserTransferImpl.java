package com.romanidze.perpenanto.dto.implementations;

import com.romanidze.perpenanto.domain.admin.ReservationToUser;
import com.romanidze.perpenanto.domain.user.Reservation;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.dto.interfaces.ReservationToUserTransfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class ReservationToUserTransferImpl implements ReservationToUserTransfer {

    @Override
    public List<ReservationToUser> getReservationsToUsers(List<User> users) {

        List<ReservationToUser> resultList = new ArrayList<>();

        int listSize = users.size();

        IntStream.range(0, listSize).forEachOrdered(i -> {

            User user = users.get(i);
            Set<Reservation> reservations = user.getReservations();

            reservations.stream()
                        .map(reservation -> ReservationToUser.builder()
                                                             .userId(user.getId())
                                                             .userReservationId(reservation.getId())
                                                             .build()
                        )
                        .forEachOrdered(resultList::add);

        });

        return resultList;

    }

}
