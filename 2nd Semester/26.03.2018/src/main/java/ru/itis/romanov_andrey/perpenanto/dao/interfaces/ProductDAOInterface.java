package ru.itis.romanov_andrey.perpenanto.dao.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Product;

import java.util.List;

public interface ProductDAOInterface extends CrudDAOInterface<Product, Long>{
    List<Product> findAllByTitle(String title);
    List<Product> findAllByPrice(Integer price);
    void updatePhoto(Product product);
}
