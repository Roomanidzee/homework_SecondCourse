package ru.itis.romanov_andrey.perpenanto.dto.implementations;

import ru.itis.romanov_andrey.perpenanto.dto.interfaces.AddressToUserTransferInterface;
import ru.itis.romanov_andrey.perpenanto.models.AddressToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempAddressToUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddressToUserTransferImpl implements AddressToUserTransferInterface {

    @Override
    public List<TempAddressToUser> getTempAddressToUsers(List<AddressToUser> oldList) {

        int addressesCount = oldList.size();

        List<Long> ids = new ArrayList<>(addressesCount);
        List<Long> userIds = new ArrayList<>(addressesCount);
        List<String> countries = new ArrayList<>(addressesCount);
        List<Integer> postIndexes = new ArrayList<>(addressesCount);
        List<String> cities = new ArrayList<>(addressesCount);
        List<String> streets = new ArrayList<>(addressesCount);
        List<Integer> homeNumbers = new ArrayList<>(addressesCount);

        int count = 1;
        int i1 = 0;

        while(i1 < addressesCount){

            ids.add((long) count);
            count++;
            i1++;

        }

        IntStream.range(0, addressesCount).forEachOrdered(i -> {
            userIds.add(oldList.get(i).getUser().getId());
            countries.add(oldList.get(i).getCountry());
            postIndexes.add(oldList.get(i).getPostIndex());
            cities.add(oldList.get(i).getCity());
            streets.add(oldList.get(i).getStreet());
            homeNumbers.add(oldList.get(i).getHomeNumber());
        });

        return IntStream.range(0, addressesCount)
                        .mapToObj(i -> TempAddressToUser.builder()
                                                      .id(ids.get(i))
                                                      .userId(userIds.get(i))
                                                      .country(countries.get(i))
                                                      .postIndex(postIndexes.get(i))
                                                      .city(cities.get(i))
                                                      .street(streets.get(i))
                                                      .homeNumber(homeNumbers.get(i))
                                                      .build()
                                 )
                        .collect(Collectors.toList());

    }
}
