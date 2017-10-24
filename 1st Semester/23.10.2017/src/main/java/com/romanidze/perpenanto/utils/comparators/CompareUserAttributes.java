package com.romanidze.perpenanto.utils.comparators;

import com.romanidze.perpenanto.models.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CompareUserAttributes implements CompareAttributes<User>{

    @Override
    public List<User> sortByAttr(List<User> oldList, String sortType) {

        List<User> resultList = new ArrayList<>();

        switch(sortType){

            case "-1":

                resultList.addAll(oldList);
                break;

            case "0":

                resultList = oldList.stream()
                                    .sorted(Comparator.comparing(User::getId).reversed())
                                    .collect(Collectors.toList());
                break;

            case "1":

                resultList = oldList.stream()
                                    .sorted(Comparator.comparing(User::getEmailOrUsername))
                                    .collect(Collectors.toList());
                break;

            case "2":

                resultList = oldList.stream()
                                    .sorted(Comparator.comparing(User::getPassword))
                                    .collect(Collectors.toList());
                break;
        }

        return resultList;

    }
}
