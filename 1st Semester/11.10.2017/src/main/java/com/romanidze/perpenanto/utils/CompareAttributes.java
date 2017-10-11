package com.romanidze.perpenanto.utils;

import com.romanidze.perpenanto.models.TempProfile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CompareAttributes {

    public List<TempProfile> sortList(List<TempProfile> oldList, String sortType){

        List<TempProfile> resultList = new ArrayList<>();

        switch(sortType){

            case "0":

                resultList = oldList.stream()
                                    .sorted(Comparator.comparing(TempProfile::getProfileId).reversed())
                                    .collect(Collectors.toList());

                break;

            case "1":

                resultList = oldList.stream()
                                    .sorted(Comparator.comparing(TempProfile::getEmail))
                                    .collect(Collectors.toList());

                break;

            case "2":

                resultList = oldList.stream()
                                    .sorted(Comparator.comparing(TempProfile::getCountry))
                                    .collect(Collectors.toList());

                break;

        }

        return resultList;

    }

}
