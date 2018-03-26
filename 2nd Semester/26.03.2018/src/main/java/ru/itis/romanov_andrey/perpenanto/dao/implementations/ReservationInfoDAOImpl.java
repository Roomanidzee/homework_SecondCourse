package ru.itis.romanov_andrey.perpenanto.dao.implementations;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProductDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProfileDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ReservationDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ReservationInfoDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.*;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Repository
public class ReservationInfoDAOImpl implements ReservationInfoDAOInterface {

    private JdbcTemplate template;
    private DataSource dataSource;
    private Map<Long, ReservationInfo> reservationInfos;

    private static final String FIND_ALL_QUERY = "SELECT * FROM reservation_info " +
            "LEFT JOIN profile ON profile.id = reservation_info.user_id " +
            "LEFT JOIN profile_info ON profile_info.id = profile.address_id " +
            "LEFT JOIN users ON users.id = profile.user_id " +
            "LEFT JOIN reservation ON reservation.id = reservation_info.reservation_id " +
            "LEFT JOIN product ON product.id = reservation_info.reservation_product_id ";
    private static final String INSERT_QUERY = "INSERT INTO " +
            "reservation_info(user_id, reservation_product_id, reservation_id) " +
            "VALUES (?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM reservation_info " +
            "WHERE reservation_info.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM reservation_info " +
            "WHERE reservation_info.id = ?";
    private static final String UPDATE_QUERY = "UPDATE reservation_info " +
            "SET(user_id, reservation_product_id, reservation_id) = (?, ?, ?)" +
            "WHERE reservation_info.id = ?";
    private static final String FIND_BY_PROFILE_QUERY = "SELECT * FROM reservation_info " +
            "WHERE reservation_info.user_id = ?";
    private static final String FIND_BY_RESERVATION_QUERY = "SELECT * FROM reservation_info " +
            "WHERE reservation_info.reservation_id = ?";
    private static final String DELETE_BY_PROFILE_QUERY = "DELETE FROM reservation_info " +
            "WHERE reservation_info.user_id = ?";

    private ReservationInfoDAOImpl(){}

    @Autowired
    public ReservationInfoDAOImpl(DataSource dataSource){

        this.dataSource = dataSource;
        this.template = new JdbcTemplate(dataSource);
        this.reservationInfos = new HashMap<>();

    }

    private RowMapper<ReservationInfo> reservationInfoRowMapper = (resultSet, rowMapper) -> {

        Long currentReservationInfoId = resultSet.getLong(1);

        if(this.reservationInfos.get(currentReservationInfoId) == null){

            this.reservationInfos.put(currentReservationInfoId, ReservationInfo.builder()
                                                                               .id(currentReservationInfoId)
                                                                               .userProfile(new Profile())
                                                                               .userReservation(new Reservation())
                                                                               .reservationProducts(Lists.newArrayList())
                                                                               .build());


        }

        if(resultSet.getLong(5) != 0){

            Profile profile = Profile.builder()
                                     .id(resultSet.getLong(5))
                                     .personName(resultSet.getString(6))
                                     .personSurname(resultSet.getString(7))
                                     .profileInfo(Lists.newArrayList())
                                     .user(new User())
                                     .build();

            this.reservationInfos.get(currentReservationInfoId).setUserProfile(profile);
        }

        if((resultSet.getLong(10) != 0) && (resultSet.getLong(17) != 0)){

            User user = User.builder()
                            .id(resultSet.getLong(17))
                            .username(resultSet.getString(18))
                            .password(resultSet.getString(19))
                            .build();

            AddressToUser addressToUser = AddressToUser.builder()
                                                 .id(resultSet.getLong(10))
                                                 .user(user)
                                                 .country(resultSet.getString(12))
                                                 .postIndex(resultSet.getInt(13))
                                                 .city(resultSet.getString(14))
                                                 .street(resultSet.getString(15))
                                                 .homeNumber(resultSet.getInt(16))
                                                 .build();

            this.reservationInfos.get(currentReservationInfoId).getUserProfile().getAddresses().add(addressToUser);

        }

        if(resultSet.getLong(17) != 0){

            User user = User.builder()
                            .id(resultSet.getLong(17))
                            .username(resultSet.getString(18))
                            .password(resultSet.getString(19))
                            .build();

            this.reservationInfos.get(currentReservationInfoId).getUserProfile().setUser(user);

        }

        if(resultSet.getLong(20) != 0){

            Reservation reservation = Reservation.builder()
                                                 .id(resultSet.getLong(20))
                                                 .status(resultSet.getString(22))
                                                 .createdAt(resultSet.getTimestamp(21))
                                                 .build();

            this.reservationInfos.get(currentReservationInfoId).setUserReservation(reservation);

        }

        if(resultSet.getLong(23) != 0){

            Product product = Product.builder()
                                     .id(resultSet.getLong(24))
                                     .title(resultSet.getString(25))
                                     .price(resultSet.getInt(26))
                                     .description(resultSet.getString(27))
                                     .photo_link(resultSet.getString(28))
                                     .build();

            this.reservationInfos.get(currentReservationInfoId).getReservationProducts().add(product);

        }

        return this.reservationInfos.get(currentReservationInfoId);

    };

    @Override
    public List<ReservationInfo> findAll() {

        this.template.query(FIND_ALL_QUERY, this.reservationInfoRowMapper);

        List<ReservationInfo> result = Lists.newArrayList(this.reservationInfos.values());

        this.reservationInfos.clear();

        return result;

    }

    @Override
    public void save(ReservationInfo model) {

        int reservationProductsCount = model.getReservationProducts().size();

        if(reservationProductsCount > 1){

            IntStream.range(0, reservationProductsCount).forEachOrdered(i -> {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                this.template.update(
                        connection -> {

                            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                            ps.setLong(1, model.getUserProfile().getId());
                            ps.setLong(2, model.getReservationProducts().get(i).getId());
                            ps.setLong(3, model.getUserReservation().getId());
                            return ps;

                        }, keyHolder);

                model.setId(keyHolder.getKey().longValue());
            });

        }

        if(reservationProductsCount == 1){

            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.template.update(
                    connection -> {

                        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                        ps.setLong(1, model.getUserProfile().getId());
                        ps.setLong(2, model.getReservationProducts().get(0).getId());
                        ps.setLong(3, model.getUserReservation().getId());
                        return ps;

                    }, keyHolder);

            model.setId(keyHolder.getKey().longValue());

        }else{

            throw new NullPointerException("В заказе нет товаров!");

        }

    }

    @Override
    public ReservationInfo find(Long id) {

        ReservationInfo result = this.template.query(FIND_BY_ID_QUERY, new Long[]{id}, this.reservationInfoRowMapper).get(0);

        this.reservationInfos.clear();

        return result;

    }

    @Override
    public void delete(Long id) {

        ReservationInfo reservationInfo = find(id);
        Profile profile = reservationInfo.getUserProfile();
        Reservation reservation = reservationInfo.getUserReservation();
        Product product = reservationInfo.getReservationProducts().get(0);

        this.template.update(DELETE_QUERY, id);

        ReservationDAOInterface reservationDAO = new ReservationDAOImpl(this.dataSource);
        ProfileDAOInterface profileDAO = new ProfileDAOImpl(this.dataSource);
        ProductDAOInterface productDAO = new ProductDAOImpl(this.dataSource);

        reservationDAO.delete(reservation.getId());
        profileDAO.delete(profile.getId());
        productDAO.delete(product.getId());

        this.reservationInfos.clear();

    }

    @Override
    public void update(ReservationInfo model) {

        int reservationProductsCount = model.getReservationProducts().size();

        if(reservationProductsCount > 1){

            Long currentUserProfileId = model.getUserProfile().getId();

            deleteAllByUserProfile(currentUserProfileId);
            save(model);

            List<Product> products = new ArrayList<>(reservationProductsCount);
            products.addAll(model.getReservationProducts());

            ProductDAOInterface productDAO = new ProductDAOImpl(this.dataSource);
            ReservationDAOInterface reservationDAO = new ReservationDAOImpl(this.dataSource);
            ProfileDAOInterface profileDAO = new ProfileDAOImpl(this.dataSource);

            reservationDAO.save(model.getUserReservation());
            profileDAO.save(model.getUserProfile());
            products.forEach(productDAO::save);

        }

        if(reservationProductsCount == 1){

            this.template.update(UPDATE_QUERY, new Long[]{model.getUserProfile().getId()},
                                               new Long[]{model.getReservationProducts().get(0).getId()},
                                               new Long[]{model.getUserReservation().getId()},
                                               new Long[]{model.getId()});

        }else{

            throw new NullPointerException("У пользователя нет заказов!");

        }

    }

    @Override
    public List<ReservationInfo> findAllByUserProfile(Profile profile) {
        this.template.query(FIND_BY_PROFILE_QUERY, new Long[]{profile.getId()}, this.reservationInfoRowMapper);

        List<ReservationInfo> result = Lists.newArrayList(this.reservationInfos.values());

        this.reservationInfos.clear();

        return result;
    }

    @Override
    public ReservationInfo findByUserReservation(Reservation reservation) {

        ReservationInfo result = this.template.query(FIND_BY_RESERVATION_QUERY,
                new Long[]{reservation.getId()}, this.reservationInfoRowMapper).get(0);
        this.reservationInfos.clear();

        return result;
    }

    @Override
    public void deleteAllByUserProfile(Long userProfileId) {

        this.template.update(DELETE_BY_PROFILE_QUERY, userProfileId);

        ProductDAOInterface productDAO = new ProductDAOImpl(this.dataSource);
        ReservationDAOInterface reservationDAO = new ReservationDAOImpl(this.dataSource);
        ProfileDAOInterface profileDAO = new ProfileDAOImpl(this.dataSource);

        productDAO.delete(productDAO.find(userProfileId).getId());
        reservationDAO.delete(reservationDAO.find(userProfileId).getId());
        profileDAO.delete(profileDAO.find(userProfileId).getId());

    }
}
