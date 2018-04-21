package com.romanidze.perpenanto.services.interfaces.admin;

import com.romanidze.perpenanto.forms.admin.ProductToUserForm;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ProductToUserAdminService {

    void addProductToUser(ProductToUserForm productToUserForm);
    void updateProductToUser(ProductToUserForm productToUserForm);
    void deleteProductToUser(Long userId, Long productId);

}
