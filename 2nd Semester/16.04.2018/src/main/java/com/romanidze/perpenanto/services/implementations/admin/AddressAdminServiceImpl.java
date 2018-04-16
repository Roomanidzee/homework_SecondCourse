package com.romanidze.perpenanto.services.implementations.admin;

import com.romanidze.perpenanto.domain.user.Address;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.admin.AddressToUserForm;
import com.romanidze.perpenanto.repositories.AddressRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.services.interfaces.admin.AddressAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 09.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class AddressAdminServiceImpl implements AddressAdminService{

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Autowired
    public AddressAdminServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addAddress(AddressToUserForm addressToUserForm) {

        User user = this.userRepository.findOne(addressToUserForm.getUserId());

        Address address = Address.builder()
                                 .country(addressToUserForm.getCountry())
                                 .postalCode(addressToUserForm.getPostalCode())
                                 .city(addressToUserForm.getCity())
                                 .street(addressToUserForm.getStreet())
                                 .homeNumber(addressToUserForm.getHomeNumber())
                                 .users(Collections.singletonList(user))
                                 .build();

        this.addressRepository.save(address);
        user.getAddresses().add(address);

    }

    @Override
    public void updateAddress(AddressToUserForm addressToUserForm) {

        User user = this.userRepository.findOne(addressToUserForm.getUserId());
        List<User> users = this.userRepository.findAll();
        Address tempAddress = this.addressRepository.findOne(addressToUserForm.getId());

        if(tempAddress == null){
            throw new NullPointerException("Address not found!");
        }

        users.stream()
                .map(User::getAddresses)
                .forEachOrdered(addresses -> addresses.forEach(
                        tempAddressAgain -> {

                            if (tempAddressAgain.equals(tempAddress)) {

                                tempAddressAgain.setCountry(addressToUserForm.getCountry());
                                tempAddressAgain.setPostalCode(addressToUserForm.getPostalCode());
                                tempAddressAgain.setCity(addressToUserForm.getCity());
                                tempAddressAgain.setStreet(addressToUserForm.getStreet());
                                tempAddressAgain.setHomeNumber(addressToUserForm.getHomeNumber());
                                tempAddressAgain.setUsers(Collections.singletonList(user));

                                this.addressRepository.save(tempAddressAgain);
                            }

                        }
                ));

    }

    @Override
    public void deleteAddress(Long userId, Integer postalCode) {

        User tempUser = this.userRepository.findOne(userId);
        Address address = this.addressRepository.findByPostalCode(postalCode);

        Set<Address> addresses = tempUser.getAddresses();

        addresses.forEach(tempAddress ->{
            if(tempAddress.equals(address)){
                tempUser.getAddresses().remove(tempAddress);
                this.userRepository.save(tempUser);
            }
        });

    }

}
