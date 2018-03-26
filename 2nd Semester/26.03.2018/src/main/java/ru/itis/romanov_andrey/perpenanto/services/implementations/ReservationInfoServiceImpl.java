package ru.itis.romanov_andrey.perpenanto.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ReservationInfoDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dto.implementations.ReservationInfoTransferImpl;
import ru.itis.romanov_andrey.perpenanto.dto.interfaces.ReservationInfoTransferInterface;
import ru.itis.romanov_andrey.perpenanto.models.ReservationInfo;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempReservationInfo;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ReservationInfoServiceInterface;
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
public class ReservationInfoServiceImpl implements ReservationInfoServiceInterface{

    @Autowired
    private ReservationInfoDAOInterface reservationInfoDAO;

    @Override
    public List<TempReservationInfo> getReservationInfos() {

        List<TempReservationInfo> resultList = new ArrayList<>();
        ReservationInfoTransferInterface reservationInfoDTO = new ReservationInfoTransferImpl();

        resultList.addAll(reservationInfoDTO.getTempReservationInfos(this.reservationInfoDAO.findAll()));
        return resultList;
    }

    @Override
    public List<TempReservationInfo> getReservationInfosByCookie(String cookieValue) {

        List<ReservationInfo> currentReservationInfos = this.reservationInfoDAO.findAll();
        List<TempReservationInfo> tempList = new ArrayList<>();
        List<TempReservationInfo> sortedList = new ArrayList<>();

        ReservationInfoTransferInterface reservationInfoDTO = new ReservationInfoTransferImpl();
        tempList.addAll(reservationInfoDTO.getTempReservationInfos(currentReservationInfos));

        int size = 3;

        Function<TempReservationInfo, String> zero = (TempReservationInfo tri) -> String.valueOf(tri.getId());
        Function<TempReservationInfo, String> first = (TempReservationInfo tri) -> String.valueOf(tri.getUserProfileId());
        Function<TempReservationInfo, String> second = (TempReservationInfo tri) -> String.valueOf(tri.getUserReservationId());

        List<Function<TempReservationInfo, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<TempReservationInfo, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<TempReservationInfo> compareAttr = new StreamCompareAttributes<>();
        sortedList.addAll(compareAttr.sortList(tempList, functionMap, cookieValue));

        return sortedList;

    }

    @Override
    public void addReservationInfo(ReservationInfo model) {
          this.reservationInfoDAO.save(model);
    }

    @Override
    public void updateReservationInfo(ReservationInfo model) {
          this.reservationInfoDAO.update(model);
    }

    @Override
    public void deleteReservationInfo(Long id) {
          this.reservationInfoDAO.delete(id);
    }
}
