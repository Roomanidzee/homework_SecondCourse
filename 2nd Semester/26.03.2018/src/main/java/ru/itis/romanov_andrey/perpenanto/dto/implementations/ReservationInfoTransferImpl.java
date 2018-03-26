package ru.itis.romanov_andrey.perpenanto.dto.implementations;

import ru.itis.romanov_andrey.perpenanto.dto.interfaces.ReservationInfoTransferInterface;
import ru.itis.romanov_andrey.perpenanto.models.*;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempReservationInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReservationInfoTransferImpl implements ReservationInfoTransferInterface{

    @Override
    public List<TempReservationInfo> getTempReservationInfos(List<ReservationInfo> oldList) {

        int reservationCount = oldList.size();

        List<Long> ids = new ArrayList<>(reservationCount);
        List<Profile> profiles = new ArrayList<>(reservationCount);
        List<Reservation> reservations = new ArrayList<>(reservationCount);
        List<Product> products = new ArrayList<>(reservationCount);

        int count = 1;

        int i1 = 0;

        while (i1 < reservationCount) {

            ids.add((long) count);
            count++;

            i1++;
        }

        IntStream.range(0, reservationCount).forEachOrdered(i -> {
            profiles.add(oldList.get(i).getUserProfile());
            reservations.add(oldList.get(i).getUserReservation());
            products.add(oldList.get(i).getReservationProducts().get(0));
        });

        return IntStream.range(0, reservationCount)
                        .mapToObj(i -> new TempReservationInfo(
                                                               ids.get(i),
                                                               profiles.get(i).getId(),
                                                               reservations.get(i).getId(),
                                                               products.get(i).getId()
                                                              )
                                  )
                        .collect(Collectors.toList());

    }
}
