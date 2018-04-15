package com.romanidze.perpenanto.repositories;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByTitle(String title);
    List<Product> findAllByPriceBetween(Integer start, Integer end);
    List<Product> findAllByReservations(List<Reservation> reservations);

}
