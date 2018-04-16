package com.romanidze.perpenanto.dto.implementations;

import com.romanidze.perpenanto.domain.admin.ReservationInfo;
import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Reservation;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.dto.interfaces.ReservationInfoTransfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class ReservationInfoTransferImpl implements ReservationInfoTransfer {

    @Override
    public List<ReservationInfo> getReservationInfos(List<User> users) {

        List<ReservationInfo> resultList = new ArrayList<>();

        users.forEach(user -> {

            Set<Reservation> reservations1 = user.getReservations();

            reservations1.forEach(reservation -> {

                List<Product> products = reservation.getProducts();
                products.stream()
                        .map(product -> ReservationInfo.builder()
                                                       .userId(user.getId())
                                                       .userReservationId(reservation.getId())
                                                       .reservationProductId(product.getId())
                                                       .build()
                        )
                        .forEachOrdered(resultList::add);

            });
        });

        return resultList;

    }
}
