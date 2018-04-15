package com.romanidze.perpenanto.repositories;

import com.romanidze.perpenanto.domain.user.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByCreatedAt(Timestamp createdAt);
    List<Reservation> findAllByStatus(String status);
    Reservation findByCreatedAt(Timestamp createdAt);

}
