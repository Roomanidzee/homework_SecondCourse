package com.romanidze.perpenanto.services.interfaces.user;

import com.romanidze.perpenanto.domain.admin.AddressToUser;
import com.romanidze.perpenanto.domain.user.Address;

import java.util.List;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface AddressService {

    List<Address> getAddresses();
    List<AddressToUser> getAddressToUsers();
    List<AddressToUser> getAddressToUsersByCookie(String cookieValue);
    void saveOrUpdate(Address address);
    void delete(Long id);

}
