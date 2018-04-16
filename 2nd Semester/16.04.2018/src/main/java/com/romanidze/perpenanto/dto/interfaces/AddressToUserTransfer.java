package com.romanidze.perpenanto.dto.interfaces;

import com.romanidze.perpenanto.domain.admin.AddressToUser;
import com.romanidze.perpenanto.domain.user.User;

import java.util.List;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface AddressToUserTransfer {

    List<AddressToUser> getAddressesToUsers(List<User> users);

}
