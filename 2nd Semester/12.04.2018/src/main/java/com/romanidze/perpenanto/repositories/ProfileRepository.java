package com.romanidze.perpenanto.repositories;

import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUser(User user);

    @Query(value = "SELECT * FROM profile WHERE user_id = :userId", nativeQuery = true)
    Profile findByUserId(@Param("userId") Long userId);


}
