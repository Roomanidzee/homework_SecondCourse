package com.romanidze.firstspringmvctask.repositories.implementations;

import com.romanidze.firstspringmvctask.domain.User;
import com.romanidze.firstspringmvctask.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 24.02.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    @Qualifier("simpleJDBC")
    private DataSource dataSource;

    private static final String FIND_QUERY = "SELECT * FROM \"user\" WHERE id = ?";

    @Override
    public User findById(Long id) {

        User user = null;

        try(Connection conn = this.dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_QUERY)){

            ps.setLong(1, id);

            try(ResultSet rs = ps.executeQuery()){

                if(rs.next()){

                    user = User.builder()
                               .id(rs.getLong(1))
                               .login(rs.getString(2))
                               .password(rs.getString(3))
                               .confirmHash(rs.getString(7))
                               .build();
                }

            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return user;

    }
}
