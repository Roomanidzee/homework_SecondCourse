package ru.itis.romanov_andrey.perpenanto.dto.implementations;

import ru.itis.romanov_andrey.perpenanto.dto.interfaces.ReservationToUserTransferInterface;
import ru.itis.romanov_andrey.perpenanto.models.Reservation;
import ru.itis.romanov_andrey.perpenanto.models.ReservationToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempReservationToUser;
import ru.itis.romanov_andrey.perpenanto.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReservationToUserTransferImpl implements ReservationToUserTransferInterface {

    @Override
    public List<TempReservationToUser> getTempReservationToUsers(List<ReservationToUser> oldList) {

        int userCount = oldList.size();

        List<User> users = new ArrayList<>(userCount);
        List<Reservation> reservations = new ArrayList<>(userCount);
        List<Long> ids = new ArrayList<>(userCount);

        int count = 1;

        for(int i = 0; i < userCount; i++){

            ids.add((long)count);
            count++;

        }

        IntStream.range(0, userCount).forEachOrdered(i -> {
            users.add(oldList.get(i).getUser());
            reservations.add(oldList.get(i).getUserReservations().get(0));
        });

        return IntStream.range(0, userCount)
                        .mapToObj(i -> new TempReservationToUser(
                                                        ids.get(i),
                                                        users.get(i).getId(),
                                                        reservations.get(i).getId()
                                                        )
                                 )
                        .collect(Collectors.toList());

    }
}
