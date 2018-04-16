package com.romanidze.perpenanto.repositories;

import com.romanidze.perpenanto.domain.user.Address;
import com.romanidze.perpenanto.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUsers(List<User> users);
    Address findByPostalCode(Integer postalCode);

}
