package ru.itis.romanov_andrey.perpenanto.dao.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Product;

import java.util.List;

/**
 * 12.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ProductDAOInterface extends CrudDAOInterface<Product, Long>{

    Product findByTitle(String title);
    List<Product> findAllByTitle(String title);

}
