package com.romanidze.perpenanto.dto.interfaces;

import com.romanidze.perpenanto.domain.admin.ProductToUser;
import com.romanidze.perpenanto.domain.user.User;

import java.util.List;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ProductToUserTransfer {

    List<ProductToUser> getProductsToUsers(List<User> users);

}
