package com.romanidze.perpenanto.services.implementations.user;

import com.romanidze.perpenanto.domain.admin.AddressToUser;
import com.romanidze.perpenanto.domain.user.Address;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.dto.interfaces.AddressToUserTransfer;
import com.romanidze.perpenanto.repositories.AddressRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.services.interfaces.user.AddressService;
import com.romanidze.perpenanto.utils.CompareAttributes;
import com.romanidze.perpenanto.utils.StreamCompareAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressToUserTransfer addressToUserDTO;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository,
                              AddressToUserTransfer addressToUserDTO) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.addressToUserDTO = addressToUserDTO;
    }

    @Override
    public List<Address> getAddresses() {
        return this.addressRepository.findAll();
    }

    @Override
    public List<AddressToUser> getAddressToUsers() {
        return this.addressToUserDTO.getAddressesToUsers(this.userRepository.findAll());
    }

    @Override
    public List<AddressToUser> getAddressToUsersByCookie(String cookieValue) {

        List<User> currentUsers = this.userRepository.findAll();
        List<AddressToUser> tempList = new ArrayList<>(this.addressToUserDTO.getAddressesToUsers(currentUsers));

        int size = 2;

        Function<AddressToUser, String> first = (AddressToUser address) -> String.valueOf(address.getUserId());
        Function<AddressToUser, String> second = (AddressToUser address) -> String.valueOf(address.getPostalCode());

        List<Function<AddressToUser, String>> functions = Arrays.asList(first, second);
        List<String> indexes = Arrays.asList("1", "2");

        Map<String, Function<AddressToUser, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<AddressToUser> compareAttr = new StreamCompareAttributes<>();

        return new ArrayList<>(compareAttr.sortList(tempList, functionMap, cookieValue));

    }

    @Override
    public void saveOrUpdate(Address address) {
        this.addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {
        this.addressRepository.delete(id);
    }
}
