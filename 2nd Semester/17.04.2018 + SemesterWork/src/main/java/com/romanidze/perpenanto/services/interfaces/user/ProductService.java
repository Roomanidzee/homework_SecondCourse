package com.romanidze.perpenanto.services.interfaces.user;

import com.romanidze.perpenanto.domain.admin.ProductToUser;
import com.romanidze.perpenanto.domain.user.Product;

import java.util.List;

/**
 * 08.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ProductService {

    List<Product> getProducts();
    List<Product> getProductsByCookie(String cookieValue);
    List<ProductToUser> getProductsToUser();
    List<ProductToUser> getProductsToUserByCookie(String cookieValue);
    void saveOrUpdate(Product product);
    void delete(Long id);

}
