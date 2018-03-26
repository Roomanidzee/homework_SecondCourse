package ru.itis.romanov_andrey.perpenanto.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ReservationToUserDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dto.implementations.ReservationToUserTransferImpl;
import ru.itis.romanov_andrey.perpenanto.dto.interfaces.ReservationToUserTransferInterface;
import ru.itis.romanov_andrey.perpenanto.models.ReservationToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempReservationToUser;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.UserInfoServiceInterface;
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
public class UserInfoServiceImpl implements UserInfoServiceInterface{

    @Autowired
    private ReservationToUserDAOInterface userInfoDAO;

    @Override
    public List<TempReservationToUser> getUserInfos() {

        List<TempReservationToUser> resultList = new ArrayList<>();
        ReservationToUserTransferInterface userInfoDTO = new ReservationToUserTransferImpl();

        resultList.addAll(userInfoDTO.getTempReservationToUsers(this.userInfoDAO.findAll()));
        return resultList;

    }

    @Override
    public List<TempReservationToUser> getUserInfoByCookie(String cookieValue) {

        List<TempReservationToUser> tempList = new ArrayList<>();
        List<TempReservationToUser> sortedList = new ArrayList<>();
        List<ReservationToUser> currentUserInfo = this.userInfoDAO.findAll();

        ReservationToUserTransferInterface userInfoDTO = new ReservationToUserTransferImpl();
        tempList.addAll(userInfoDTO.getTempReservationToUsers(currentUserInfo));

        int size = 3;

        Function<TempReservationToUser, String> zero = (TempReservationToUser tui) -> String.valueOf(tui.getId());
        Function<TempReservationToUser, String> first = (TempReservationToUser tui) -> String.valueOf(tui.getUserId());
        Function<TempReservationToUser, String> second = (TempReservationToUser tui) -> String.valueOf(tui.getUserReservationId());

        List<Function<TempReservationToUser, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<TempReservationToUser, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<TempReservationToUser> compareAttr = new StreamCompareAttributes<>();
        sortedList.addAll(compareAttr.sortList(tempList, functionMap, cookieValue));

        return sortedList;

    }

    @Override
    public void addUserInfo(ReservationToUser model) {
        this.userInfoDAO.save(model);
    }

    @Override
    public void updateUserInfo(ReservationToUser model) {
        this.userInfoDAO.update(model);
    }

    @Override
    public void deleteUserInfo(Long id) {
        this.userInfoDAO.delete(id);
    }
}
