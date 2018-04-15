package com.romanidze.perpenanto.repositories;

import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.security.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    Optional<User> findById(Long userId);
    Optional<User> findByConfirmHash(String confirmHash);
    List<User> findAllByRole(Role role);

}
