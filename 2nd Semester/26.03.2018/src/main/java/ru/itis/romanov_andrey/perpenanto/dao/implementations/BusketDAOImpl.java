package ru.itis.romanov_andrey.perpenanto.dao.implementations;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.BusketDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.*;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Repository
public class BusketDAOImpl implements BusketDAOInterface {

    private JdbcTemplate template;
    private Map<Long, Busket> buskets;

    private static final String FIND_ALL_QUERY = "SELECT * FROM busket " +
            "LEFT JOIN profile ON profile.id = busket.user_id " +
            "LEFT JOIN profile_info ON profile_info.id = profile.address_id " +
            "LEFT JOIN users ON users.id = profile.user_id " +
            "LEFT JOIN product ON product.id = busket.reservation_product_id";

    private static final String INSERT_QUERY = "INSERT INTO busket(user_id, reservation_product_id) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM busket WHERE busket.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM busket WHERE busket.id = ?";
    private static final String UPDATE_QUERY = "UPDATE busket SET(user_id, reservation_product_id) = (?, ?) " +
                                                 "WHERE busket.id = ?";
    private static final String FIND_BY_PROFILE_QUERY = "SELECT * FROM busket WHERE busket.user_id = ?";
    private static final String DELETE_BY_PROFILE_QUERY = "DELETE FROM busket WHERE busket.user_id = ? ";

    private BusketDAOImpl() {
    }

    @Autowired
    public BusketDAOImpl(DataSource dataSource) {

        this.template = new JdbcTemplate(dataSource);
        this.buskets = new HashMap<>();

    }

    private RowMapper<Busket> busketRowMapper = (resultSet, rowMapper) -> {

        Long currentBusketId = resultSet.getLong(1);

        if (this.buskets.get(currentBusketId) == null) {

            this.buskets.put(currentBusketId, Busket.builder()
                                                    .id(currentBusketId)
                                                    .userProfile(new Profile())
                                                    .products(Lists.newArrayList())
                                                    .build());

        }

        if (resultSet.getLong(4) != 0) {

            Profile profile = Profile.builder()
                                     .id(resultSet.getLong(4))
                                     .personName(resultSet.getString(5))
                                     .personSurname(resultSet.getString(6))
                                     .user(new User())
                                     .profileInfo(Lists.newArrayList())
                                     .build();

            this.buskets.get(currentBusketId).setUserProfile(profile);
        }
        
        if((resultSet.getLong(9) != 0) && (resultSet.getLong(16) != 0)){

            User user = User.builder()
                            .id(resultSet.getLong(16))
                            .username(resultSet.getString(17))
                            .password(resultSet.getString(18))
                            .build();

            AddressToUser addressToUser = AddressToUser.builder()
                                                 .id(resultSet.getLong(9))
                                                 .user(user)
                                                 .country(resultSet.getString(11))
                                                 .postIndex(resultSet.getInt(12))
                                                 .city(resultSet.getString(13))
                                                 .street(resultSet.getString(14))
                                                 .homeNumber(resultSet.getInt(15))
                                                 .build();
            
            this.buskets.get(currentBusketId).getUserProfile().getAddresses().add(addressToUser);
            
        }

        if (resultSet.getLong(16) != 0) {

            User user = User.builder()
                            .id(resultSet.getLong(16))
                            .username(resultSet.getString(17))
                            .password(resultSet.getString(18))
                            .build();

            this.buskets.get(currentBusketId).getUserProfile().setUser(user);

        }

        if (resultSet.getLong(19) != 0) {

            Product product = Product.builder()
                                     .id(resultSet.getLong(19))
                                     .title(resultSet.getString(20))
                                     .price(resultSet.getInt(21))
                                     .description(resultSet.getString(22))
                                     .photo_link(resultSet.getString(23))
                                     .build();

            this.buskets.get(currentBusketId).getProducts().add(product);

        }

        return this.buskets.get(currentBusketId);
    };

    @Override
    public List<Busket> findAll() {

        this.template.query(FIND_ALL_QUERY, this.busketRowMapper);

        List<Busket> result = Lists.newArrayList(this.buskets.values());

        this.buskets.clear();
        return result;

    }

    @Override
    public void save(Busket model) {

        int productsCount = model.getProducts().size();

        if (productsCount > 1) {

            IntStream.range(0, productsCount).forEachOrdered(i -> {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                this.template.update(
                        connection -> {

                            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                            ps.setLong(1, model.getUserProfile().getId());
                            ps.setLong(2, model.getProducts().get(i).getId());
                            return ps;

                        }, keyHolder);

                model.setId(keyHolder.getKey().longValue());
            });

        }
        if (productsCount == 1) {

            KeyHolder keyHolder = new GeneratedKeyHolder();

            this.template.update(
                    connection -> {

                        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                        ps.setLong(1, model.getUserProfile().getId());
                        ps.setLong(2, model.getProducts().get(0).getId());
                        return ps;

                    }, keyHolder);

            model.setId(keyHolder.getKey().longValue());

        } else {

            throw new NullPointerException("В корзине нет товаров!");

        }

    }

    @Override
    public Busket find(Long id) {

        Busket result = this.template.query(FIND_BY_ID_QUERY, new Long[]{id}, this.busketRowMapper).get(0);

        this.buskets.clear();

        return result;

    }

    @Override
    public void delete(Long id) {

        this.template.update(DELETE_QUERY, id);
        this.buskets.clear();

    }

    @Override
    public void update(Busket model) {

        int productsCount = model.getProducts().size();

        if (productsCount > 1) {

            Long currentUserProfileId = model.getUserProfile().getId();

            deleteByUserProfile(currentUserProfileId);
            save(model);

        }

        if (productsCount == 1) {

            this.template.update(UPDATE_QUERY, new Long[]{model.getUserProfile().getId()},
                    new Long[]{model.getProducts().get(0).getId()},
                    new Long[]{model.getId()});

        } else {

            throw new NullPointerException("В корзине нет товаров!");

        }

    }

    @Override
    public List<Busket> findAllByUserProfile(Profile profile) {

        this.template.query(FIND_BY_PROFILE_QUERY, new Long[]{profile.getId()}, this.busketRowMapper);

        List<Busket> result = Lists.newArrayList(this.buskets.values());

        this.buskets.clear();

        return result;

    }

    @Override
    public void deleteByUserProfile(Long userProfileId) {

        this.template.update(DELETE_BY_PROFILE_QUERY, userProfileId);

    }
}

