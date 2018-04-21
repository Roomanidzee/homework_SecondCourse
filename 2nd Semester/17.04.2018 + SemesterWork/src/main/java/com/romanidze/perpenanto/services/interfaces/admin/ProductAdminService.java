package com.romanidze.perpenanto.services.interfaces.admin;

import com.romanidze.perpenanto.forms.admin.ProductForm;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ProductAdminService {

    void addProduct(ProductForm productForm);
    void updateProduct(ProductForm productForm);
    void deleteProduct(Long id);

}
