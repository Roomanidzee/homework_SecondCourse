package ru.itis.romanov_andrey.perpenanto.dao.implementations;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ReservationDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Reservation;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationDAOImpl implements ReservationDAOInterface {

    private JdbcTemplate template;
    private Map<Long, Reservation> reservations;

    private static final String FIND_ALL_QUERY = "SELECT * FROM reservation";
    private static final String INSERT_QUERY = "INSERT INTO reservation(status, created_at) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM reservation WHERE reservation.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM reservation WHERE reservation.id = ?";
    private static final String UPDATE_QUERY = "UPDATE reservation SET(status, created_at) = (?, ?) " +
            "WHERE reservation.id = ?";
    private static final String FIND_ALL_BY_STATUS_QUERY = "SELECT * FROM reservation " +
            "WHERE reservation.status = ?";
    private static final String FIND_ALL_BY_TIMESTAMP_QUERY = "SELECT * FROM reservation " +
            "WHERE reservation.created_at = ?";

    private ReservationDAOImpl(){}

    @Autowired
    public ReservationDAOImpl(DataSource dataSource){

        this.template = new JdbcTemplate(dataSource);
        this.reservations = new HashMap<>();

    }

    private RowMapper<Reservation> reservationRowMapper = (resultSet, rowNumber) ->{

        Long currentReservationId = resultSet.getLong(1);

        if(this.reservations.get(currentReservationId) == null){

            this.reservations.put(currentReservationId, Reservation.builder()
                    .status(resultSet.getString(3))
                    .createdAt(resultSet.getTimestamp(2))
                    .build());
        }

        return this.reservations.get(currentReservationId);

    };

    @Override
    public List<Reservation> findAll() {

        this.template.query(FIND_ALL_QUERY, this.reservationRowMapper);

        List<Reservation> result = Lists.newArrayList(this.reservations.values());

        this.reservations.clear();

        return result;

    }

    @Override
    public void save(Reservation model) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(
                connection -> {

                    PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                    ps.setString(1, model.getStatus());
                    ps.setTimestamp(2, model.getCreatedAt());
                    return ps;

                }, keyHolder);

        model.setId(keyHolder.getKey().longValue());

    }

    @Override
    public Reservation find(Long id) {

        Reservation result = this.template.query(FIND_BY_ID_QUERY, new Long[]{id}, this.reservationRowMapper).get(0);

        this.reservations.clear();

        return result;

    }

    @Override
    public void delete(Long id) {

        this.template.update(DELETE_QUERY, id);
        this.reservations.clear();

    }

    @Override
    public void update(Reservation model) {

        this.template.update(UPDATE_QUERY, new String[]{model.getStatus()}, new Timestamp[]{model.getCreatedAt()});

    }

    @Override
    public List<Reservation> findAllByStatus(String status) {

        this.template.query(FIND_ALL_BY_STATUS_QUERY, new String[]{status}, this.reservationRowMapper);

        List<Reservation> result = Lists.newArrayList(this.reservations.values());

        this.reservations.clear();

        return result;

    }

    @Override
    public List<Reservation> findAllByTimeStamp(Timestamp timestamp) {

        this.template.query(FIND_ALL_BY_TIMESTAMP_QUERY, new Timestamp[]{timestamp}, this.reservationRowMapper);

        List<Reservation> result = Lists.newArrayList(this.reservations.values());

        this.reservations.clear();

        return result;

    }
}

