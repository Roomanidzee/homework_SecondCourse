package com.romanidze.perpenanto.services.implementations.user;

import com.romanidze.perpenanto.domain.admin.ReservationInfo;
import com.romanidze.perpenanto.domain.admin.ReservationToUser;
import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.Reservation;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.dto.interfaces.ReservationInfoTransfer;
import com.romanidze.perpenanto.dto.interfaces.ReservationToUserTransfer;
import com.romanidze.perpenanto.repositories.ReservationRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.services.interfaces.user.BusketService;
import com.romanidze.perpenanto.services.interfaces.user.ReservationService;
import com.romanidze.perpenanto.utils.CompareAttributes;
import com.romanidze.perpenanto.utils.StreamCompareAttributes;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationServiceImpl implements ReservationService{

    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private BusketService busketService;
    private ReservationToUserTransfer reservationToUserDTO;
    private ReservationInfoTransfer reservationInfoDTO;

    @Override
    public List<Reservation> getReservations() {
        return this.reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationsByCookie(String cookieValue) {

        List<Reservation> currentReservations = this.reservationRepository.findAll();

        int size = 3;

        Function<Reservation, String> zero = (Reservation r) -> String.valueOf(r.getId());
        Function<Reservation, String> first = (Reservation r) -> r.getStatus().toString();
        Function<Reservation, String> second = (Reservation r) -> String.valueOf(r.getCreatedAt());

        List<Function<Reservation, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<Reservation, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<Reservation> compareAttr = new StreamCompareAttributes<>();

        return new ArrayList<>(compareAttr.sortList(currentReservations, functionMap, cookieValue));

    }

    @Override
    public List<ReservationInfo> getReservationInfosByCookie(String cookieValue) {

        List<User> currentUsers = this.userRepository.findAll();
        List<ReservationInfo> tempList = new ArrayList<>(this.reservationInfoDTO.getReservationInfos(currentUsers));

        int size = 2;

        Function<ReservationInfo, String> first = (ReservationInfo ri) -> String.valueOf(ri.getUserId());
        Function<ReservationInfo, String> second = (ReservationInfo ri) -> String.valueOf(ri.getUserReservationId());

        List<Function<ReservationInfo, String>> functions = Arrays.asList(first, second);
        List<String> indexes = Arrays.asList("1", "2");

        Map<String, Function<ReservationInfo, String>> functionMap = new HashMap<>();
        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<ReservationInfo> compareAttr = new StreamCompareAttributes<>();

        return new ArrayList<>(compareAttr.sortList(tempList, functionMap, cookieValue));

    }

    @Override
    public List<ReservationToUser> getReservationToUsersByCookie(String cookieValue) {

        List<User> currentUsers = this.userRepository.findAll();
        List<ReservationToUser> tempList = new ArrayList<>(this.reservationToUserDTO.getReservationsToUsers(currentUsers));

        int size = 2;

        Function<ReservationToUser, String> first = (ReservationToUser rtu) -> String.valueOf(rtu.getUserId());
        Function<ReservationToUser, String> second = (ReservationToUser rtu) -> String.valueOf(rtu.getUserReservationId());

        List<Function<ReservationToUser, String>> functions = Arrays.asList(first, second);
        List<String> indexes = Arrays.asList("1", "2");

        Map<String, Function<ReservationToUser, String>> functionMap = new HashMap<>();
        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<ReservationToUser> compareAttr = new StreamCompareAttributes<>();

        return new ArrayList<>(compareAttr.sortList(tempList, functionMap, cookieValue));

    }

    @Override
    public void saveOrUpdate(Reservation reservation) {
        this.reservationRepository.save(reservation);
    }

    @Override
    public void delete(Long id) {
        this.reservationRepository.delete(id);
    }

    @Override
    public Long getReservationId(Timestamp timestamp) {

        Reservation reservation = this.reservationRepository.findByCreatedAt(timestamp);

        return reservation.getId();
    }

    @Override
    public List<Product> getReservationProducts(Profile profile, Timestamp timestamp) {

        Reservation reservation = this.reservationRepository.findByCreatedAt(timestamp);
        this.busketService.payForBusket(profile, reservation);

        return reservation.getProducts();
    }

    @Override
    public Integer getReservationPrice(Timestamp timestamp) {

        Reservation reservation = this.reservationRepository.findByCreatedAt(timestamp);
        List<Product> products = reservation.getProducts();

        return products.stream()
                       .mapToInt(Product::getPrice)
                       .sum();

    }

    @Override
    public Reservation getReservationByTimestamp(Timestamp timestamp) {
        return this.reservationRepository.findByCreatedAt(timestamp);
    }
}
