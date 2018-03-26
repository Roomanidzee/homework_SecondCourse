package ru.itis.romanov_andrey.perpenanto.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.AddressToUserDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dto.implementations.AddressToUserTransferImpl;
import ru.itis.romanov_andrey.perpenanto.dto.interfaces.AddressToUserTransferInterface;
import ru.itis.romanov_andrey.perpenanto.models.AddressToUser;
import ru.itis.romanov_andrey.perpenanto.models.User;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempAddressToUser;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProfileInfoServiceInterface;
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
public class ProfileInfoServiceImpl implements ProfileInfoServiceInterface{

    @Autowired
    private AddressToUserDAOInterface profileInfoDAO;

    @Override
    public List<AddressToUser> findAddressByUser(User user) {
        return this.profileInfoDAO.findByUser(user);
    }

    @Override
    public List<TempAddressToUser> getProfileInfos() {

        List<TempAddressToUser> resultList = new ArrayList<>();
        AddressToUserTransferInterface profileInfoDTO = new AddressToUserTransferImpl();

        resultList.addAll(profileInfoDTO.getTempAddressToUsers(this.profileInfoDAO.findAll()));
        return resultList;

    }

    @Override
    public List<TempAddressToUser> getProfileInfosByCookie(String cookieValue) {

        List<AddressToUser> currentProfileInfos = this.profileInfoDAO.findAll();
        List<TempAddressToUser> tempList = new ArrayList<>();
        List<TempAddressToUser> sortedList = new ArrayList<>();

        AddressToUserTransferInterface profileInfoDTO = new AddressToUserTransferImpl();
        tempList.addAll(profileInfoDTO.getTempAddressToUsers(currentProfileInfos));

        int size = 3;

        Function<TempAddressToUser, String> zero = (TempAddressToUser tpi) -> String.valueOf(tpi.getId());
        Function<TempAddressToUser, String> first = (TempAddressToUser tpi) -> String.valueOf(tpi.getUserId());
        Function<TempAddressToUser, String> second = (TempAddressToUser tpi) -> String.valueOf(tpi.getPostIndex());

        List<Function<TempAddressToUser, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<TempAddressToUser, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<TempAddressToUser> compareAttr = new StreamCompareAttributes<>();
        sortedList.addAll(compareAttr.sortList(tempList, functionMap, cookieValue));

        return sortedList;

    }

    @Override
    public void addProfileInfo(AddressToUser model) {
        this.profileInfoDAO.save(model);
    }

    @Override
    public void updateProfileInfo(AddressToUser model) {
        this.profileInfoDAO.update(model);
    }

    @Override
    public void deleteProfileInfo(Long id) {
        this.profileInfoDAO.delete(id);
    }
}
