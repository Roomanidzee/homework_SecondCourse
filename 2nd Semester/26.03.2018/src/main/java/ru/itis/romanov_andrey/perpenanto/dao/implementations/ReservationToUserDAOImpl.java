package ru.itis.romanov_andrey.perpenanto.dao.implementations;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ReservationDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ReservationToUserDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Reservation;
import ru.itis.romanov_andrey.perpenanto.models.ReservationToUser;
import ru.itis.romanov_andrey.perpenanto.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Repository
public class ReservationToUserDAOImpl implements ReservationToUserDAOInterface {

    private DataSource dataSource;
    private JdbcTemplate template;
    private Map<Long, ReservationToUser> userInfos;

    private static final String FIND_ALL_QUERY = "SELECT * FROM user_info " +
            "LEFT JOIN users ON users.id = user_info.user_id " +
            "LEFT JOIN reservation ON reservation.id = user_info.user_reservation_id";
    private static final String INSERT_QUERY = "INSERT INTO user_info(user_id, user_reservation_id) " +
            "VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM user_info WHERE user_info.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user_info WHERE user_info.id = ?";
    private static final String UPDATE_QUERY = "UPDATE user_info SET(user_id, user_reservation_id) = (?, ?) " +
            "WHERE user_info.id = ?";
    private static final String FIND_ALL_BY_USER_QUERY = "SELECT * FROM user_info WHERE user_info.user_id = ?";
    private static final String DELETE_ALL_BY_USER_QUERY = "DELETE FROM user_info WHERE user_info.user_id = ?";

    private ReservationToUserDAOImpl(){}

    @Autowired
    public ReservationToUserDAOImpl(DataSource dataSource){

        this.dataSource = dataSource;
        this.template = new JdbcTemplate(dataSource);
        this.userInfos = new HashMap<>();

    }

    private RowMapper<ReservationToUser> userInfoRowMapper = (resultSet, rowMapper) ->{

        Long currentUserInfoId = resultSet.getLong(1);

        if(this.userInfos.get(currentUserInfoId) == null){

            this.userInfos.put(currentUserInfoId, ReservationToUser.builder()
                    .id(currentUserInfoId)
                    .user(new User())
                    .userReservations(Lists.newArrayList())
                    .build());

        }

        if(resultSet.getLong(2) != 0){

            User user = User.builder()
                    .id(resultSet.getLong(2))
                    .username(resultSet.getString(5))
                    .password(resultSet.getString(6))
                    .build();

            this.userInfos.get(currentUserInfoId).setUser(user);

        }

        if(resultSet.getLong(3) != 0){

            Reservation reservation = Reservation.builder()
                    .id(resultSet.getLong(7))
                    .status(resultSet.getString(9))
                    .createdAt(resultSet.getTimestamp(8))
                    .build();

            this.userInfos.get(currentUserInfoId).getUserReservations().add(reservation);

        }

        return this.userInfos.get(currentUserInfoId);

    };

    @Override
    public List<ReservationToUser> findAll() {

        this.template.query(FIND_ALL_QUERY, this.userInfoRowMapper);

        List<ReservationToUser> result = Lists.newArrayList(this.userInfos.values());

        this.userInfos.clear();

        return result;

    }

    @Override
    public void save(ReservationToUser model) {

        int reservationsCount = model.getUserReservations().size();

        if(reservationsCount > 1){

            IntStream.range(0, reservationsCount).forEachOrdered(i -> {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                this.template.update(
                        connection -> {

                            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                            ps.setLong(1, model.getUser().getId());
                            ps.setLong(2, model.getUserReservations().get(i).getId());
                            return ps;

                        }, keyHolder);
                model.setId(keyHolder.getKey().longValue());
            });

        }

        if(reservationsCount == 1){

            KeyHolder keyHolder = new GeneratedKeyHolder();

            this.template.update(
                    connection -> {

                        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                        ps.setLong(1, model.getUser().getId());
                        ps.setLong(2, model.getUserReservations().get(0).getId());
                        return ps;

                    }, keyHolder);

            model.setId(keyHolder.getKey().longValue());

        }else{

            throw new NullPointerException("У пользователя нет заказов!");

        }

    }

    @Override
    public ReservationToUser find(Long id) {

        ReservationToUser result = this.template.query(FIND_BY_ID_QUERY, new Long[]{id}, this.userInfoRowMapper).get(0);

        this.userInfos.clear();

        return result;

    }

    @Override
    public void delete(Long id) {

        ReservationToUser reservationToUser = find(id);

        Long reservationId = reservationToUser.getUserReservations().get(0).getId();

        ReservationDAOInterface reservationDAO = new ReservationDAOImpl(this.dataSource);
        reservationDAO.delete(reservationId);

        this.template.update(DELETE_QUERY, id);
        this.userInfos.clear();

    }

    @Override
    public void update(ReservationToUser model) {

        int reservationsCount = model.getUserReservations().size();

        if(reservationsCount > 1){

            Long currentUserId = model.getUser().getId();

            deleteAllByUser(currentUserId);

            save(model);

            List<Reservation> reservations = new ArrayList<>(reservationsCount);
            reservations.addAll(model.getUserReservations());

            ReservationDAOInterface reservationDAO = new ReservationDAOImpl(this.dataSource);
            reservations.forEach(reservationDAO::save);

        }

        if(reservationsCount == 1){

            this.template.update(UPDATE_QUERY, new Long[]{model.getUser().getId()},
                                               new Long[]{model.getUserReservations().get(0).getId()},
                                               new Long[]{model.getId()});

        }else{

            throw new NullPointerException("У пользователя нет заказов!");

        }

    }

    @Override
    public List<ReservationToUser> findAllByUser(User user) {

        this.template.query(FIND_ALL_BY_USER_QUERY, new Long[]{user.getId()}, this.userInfoRowMapper);

        List<ReservationToUser> result = Lists.newArrayList(this.userInfos.values());

        this.userInfos.clear();

        return result;

    }

    @Override
    public void deleteAllByUser(Long userId) {

        this.template.update(DELETE_ALL_BY_USER_QUERY, userId);

        ReservationDAOInterface reservationDAO = new ReservationDAOImpl(this.dataSource);

        Reservation reservation = reservationDAO.find(userId);
        reservationDAO.delete(reservation.getId());

    }

}

