package com.romanidze.perpenanto.services.interfaces.admin;

import com.romanidze.perpenanto.forms.admin.AddressToUserForm;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface AddressAdminService {

    void addAddress(AddressToUserForm addressToUserForm);
    void updateAddress(AddressToUserForm addressToUserForm);
    void deleteAddress(Long userId, Integer postalCode);

}
