package com.romanidze.perpenanto.dto.implementations;

import com.romanidze.perpenanto.domain.admin.AddressToUser;
import com.romanidze.perpenanto.domain.user.Address;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.dto.interfaces.AddressToUserTransfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class AddressToUserTransferImpl implements AddressToUserTransfer {

    @Override
    public List<AddressToUser> getAddressesToUsers(List<User> users) {

        List<AddressToUser> resultList = new ArrayList<>();

        int listSize = users.size();

        IntStream.range(0, listSize).forEachOrdered(i -> {
            User user = users.get(i);
            Set<Address> addresses = user.getAddresses();
            addresses.stream()
                     .map(address -> AddressToUser.builder()
                                                  .userId(user.getId())
                                                  .country(address.getCountry())
                                                  .postalCode(address.getPostalCode())
                                                  .city(address.getCity())
                                                  .street(address.getStreet())
                                                  .homeNumber(address.getHomeNumber())
                                                  .build()
                     )
                     .forEachOrdered(resultList::add);
        });

        return resultList;

    }
}
