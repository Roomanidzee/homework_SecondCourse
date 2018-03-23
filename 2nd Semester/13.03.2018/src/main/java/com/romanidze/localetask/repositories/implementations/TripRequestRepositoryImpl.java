package com.romanidze.localetask.repositories.implementations;

import com.romanidze.localetask.domain.TripRequest;
import com.romanidze.localetask.forms.TripRequestForm;
import com.romanidze.localetask.repositories.interfaces.TripRequestRepository;
import com.romanidze.localetask.utils.ConstantsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 23.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Repository
public class TripRequestRepositoryImpl implements TripRequestRepository {

    private final DataSource dataSource;
    private final ConstantsClass constants;

    private static final String FIND_ALL_QUERY = "SELECT * FROM trip_request";
    private static final String SAVE_QUERY =
            "INSERT INTO trip_request(country, departure_date, arrival_date) VALUES(?, ?, ?)";

    @Autowired
    public TripRequestRepositoryImpl(@Qualifier("simpleJDBC") DataSource dataSource, ConstantsClass constants) {
        this.dataSource = dataSource;
        this.constants = constants;
    }

    @Override
    public void saveTripRequest(TripRequestForm tripRequestForm) {

        try(Connection conn = this.dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(SAVE_QUERY)){

            ps.setString(1, tripRequestForm.getCountry());
            ps.setString(2, tripRequestForm.getDepartureDate());
            ps.setString(3, tripRequestForm.getArrivalDate());
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<TripRequest> findAll() {

        List<TripRequest> requestList = new ArrayList<>();

        try(Connection conn = this.dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                TripRequest request =
                        TripRequest.builder()
                                   .id(rs.getLong(1))
                                   .country(rs.getString(2))
                                   .departureDate(LocalDateTime.parse(rs.getString(3),
                                           this.constants.getFormatter()))
                                   .arrivalDate(LocalDateTime.parse(rs.getString(4),
                                           this.constants.getFormatter()))
                                   .build();
                requestList.add(request);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return requestList;

    }
}
