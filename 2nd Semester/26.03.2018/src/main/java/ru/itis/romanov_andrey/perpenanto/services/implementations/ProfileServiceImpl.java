package ru.itis.romanov_andrey.perpenanto.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProfileDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dto.implementations.ProfileTransferImpl;
import ru.itis.romanov_andrey.perpenanto.dto.interfaces.ProfileTransferInterface;
import ru.itis.romanov_andrey.perpenanto.models.Profile;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempProfile;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProfileServiceInterface;
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
public class ProfileServiceImpl implements ProfileServiceInterface{

    @Autowired
    private ProfileDAOInterface profileDAO;

    @Override
    public List<TempProfile> getProfiles() {

        List<TempProfile> resultList = new ArrayList<>();
        ProfileTransferInterface profileDTO = new ProfileTransferImpl();

        resultList.addAll(profileDTO.getTempProfiles(this.profileDAO.findAll()));
        return resultList;

    }

    @Override
    public List<TempProfile> getProfilesByCookie(String cookieValue) {

        List<Profile> currentProfiles = this.profileDAO.findAll();
        List<TempProfile> tempList = new ArrayList<>();
        List<TempProfile> sortedList = new ArrayList<>();

        ProfileTransferInterface profileDTO = new ProfileTransferImpl();
        tempList.addAll(profileDTO.getTempProfiles(currentProfiles));

        int size = 3;

        Function<TempProfile, String> zero = (TempProfile tp) -> String.valueOf(tp.getId());
        Function<TempProfile, String> first = TempProfile::getPersonName;
        Function<TempProfile, String> second = TempProfile::getPersonSurname;

        List<Function<TempProfile, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<TempProfile, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<TempProfile> compareAttr = new StreamCompareAttributes<>();

        sortedList.addAll(compareAttr.sortList(tempList, functionMap, cookieValue));

        return sortedList;

    }

    @Override
    public void addProfile(Profile model) {
        this.profileDAO.save(model);
    }

    @Override
    public void updateProfile(Profile model) {
        this.profileDAO.update(model);
    }

    @Override
    public void deleteProfile(Long id) {
        this.profileDAO.delete(id);
    }

    @Override
    public Profile getProfileById(Long id) {
        return this.profileDAO.find(id);
    }
}
