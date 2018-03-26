package ru.itis.romanov_andrey.perpenanto.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ReservationDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Reservation;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ReservationsServiceInterface;
import ru.itis.romanov_andrey.perpenanto.utils.CompareAttributes;
import ru.itis.romanov_andrey.perpenanto.utils.StreamCompareAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

@Service
public class ReservationsServiceImpl implements ReservationsServiceInterface{

    @Autowired
    private ReservationDAOInterface reservationDAO;

    @Override
    public List<Reservation> getReservations() {
        return this.reservationDAO.findAll();
    }

    @Override
    public List<Reservation> getReservationByCookie(String cookieValue) {

        List<Reservation> currentReservations = this.reservationDAO.findAll();
        List<Reservation> sortedReservations = new ArrayList<>();

        int size = 3;

        Function<Reservation, String> zero = (Reservation r) -> String.valueOf(r.getId());
        Function<Reservation, String> first = Reservation::getStatus;
        Function<Reservation, String> second = (Reservation r) -> String.valueOf(r.getCreatedAt());

        List<Function<Reservation, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<Reservation, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<Reservation> compareAttr = new StreamCompareAttributes<>();

        sortedReservations.addAll(compareAttr.sortList(currentReservations, functionMap, cookieValue));

        return sortedReservations;

    }

    @Override
    public void addReservation(Reservation model) {
        this.reservationDAO.save(model);
    }

    @Override
    public void updateReservation(Reservation model) {
        this.reservationDAO.update(model);
    }

    @Override
    public void deleteReservation(Long id) {
        this.reservationDAO.delete(id);
    }

    @Override
    public Reservation getReservationById(Long id) {
        return this.reservationDAO.find(id);
    }
}
