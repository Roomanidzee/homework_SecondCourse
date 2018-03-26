package ru.itis.romanov_andrey.perpenanto.services.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Product;

import java.util.List;

public interface ProductServiceInterface {

    List<Product> getProducts();
    List<Product> getProductsByCookie(String cookieValue);
    void addProduct(Product model);
    void updateProduct(Product model);
    void deleteProduct(Long id);
    Product getProductById(Long id);

}
