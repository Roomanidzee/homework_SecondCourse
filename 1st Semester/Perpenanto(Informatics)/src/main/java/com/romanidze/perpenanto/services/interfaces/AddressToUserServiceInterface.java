package com.romanidze.perpenanto.services.interfaces;

import com.romanidze.perpenanto.models.AddressToUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AddressToUserServiceInterface {

    List<AddressToUser> getAddressToUsersByCookie(HttpServletRequest req, HttpServletResponse resp);

}
