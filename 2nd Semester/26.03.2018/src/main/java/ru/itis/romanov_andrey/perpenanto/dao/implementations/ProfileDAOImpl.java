package ru.itis.romanov_andrey.perpenanto.dao.implementations;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProfileDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.AddressToUserDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.UserDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Profile;
import ru.itis.romanov_andrey.perpenanto.models.AddressToUser;
import ru.itis.romanov_andrey.perpenanto.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Repository
public class ProfileDAOImpl implements ProfileDAOInterface {

    private JdbcTemplate template;
    private Map<Long, Profile> profiles;
    private DataSource datasource;

    private static final String FIND_ALL_QUERY = "SELECT * FROM profile " +
            "LEFT JOIN users ON users.id = profile.user_id " +
            "LEFT JOIN profile_info ON profile_info.id = profile.address_id";
    private static final String INSERT_QUERY =
            "INSERT INTO profile(person_name, person_surname, user_id, address_id) " +
                    "VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM profile, users " +
            "WHERE profile.id = ? AND profile.user_id = users.id";
    private static final String DELETE_QUERY = "DELETE FROM profile " +
            "WHERE profile.id = ? AND profile.user_id = users.id";
    private static final String UPDATE_QUERY = "UPDATE profile " +
            "SET(person_name, person_surname, user_id, address_id) = (?, ?, ? ,?)" +
            "WHERE profile.id = ?";
    private static final String FIND_BY_USER_QUERY = "SELECT * FROM profile, users " +
            "WHERE users.id = ? AND profile.user_id = users.id";

    private ProfileDAOImpl(){}

    @Autowired
    public ProfileDAOImpl(DataSource dataSource){

        this.template = new JdbcTemplate(dataSource);
        this.datasource = dataSource;
        this.profiles = new HashMap<>();

    }

    private RowMapper<Profile> profileRowMapper = (resultSet, rowNumber) ->{

        Long currentProfileId = resultSet.getLong(1);

        if(this.profiles.get(currentProfileId) == null){

            this.profiles.put(currentProfileId, Profile.builder()
                                                       .id(currentProfileId)
                                                       .personName(resultSet.getString(2))
                                                       .personSurname(resultSet.getString(3))
                                                       .user(new User())
                                                       .profileInfo(Lists.newArrayList())
                                                       .build());

        }

        if(resultSet.getLong(6) != 0){

            User user = User.builder()
                            .id(resultSet.getLong(6))
                            .username(resultSet.getString(7))
                            .password(resultSet.getString(8))
                            .build();

            this.profiles.get(currentProfileId).setUser(user);

        }

        if(resultSet.getLong(9) != 0){

            User user = User.builder()
                            .id(resultSet.getLong(6))
                            .username(resultSet.getString(7))
                            .password(resultSet.getString(8))
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

            this.profiles.get(currentProfileId).getAddresses().add(addressToUser);

        }

        return this.profiles.get(currentProfileId);

    };

    @Override
    public List<Profile> findAll() {

        this.template.query(FIND_ALL_QUERY, this.profileRowMapper);

        List<Profile> result = Lists.newArrayList(this.profiles.values());

        this.profiles.clear();
        return result;

    }

    @Override
    public void save(Profile model) {

        int addressesCount = model.getAddresses().size();

        if(addressesCount > 1){

            List<AddressToUser> addresses = model.getAddresses();

            IntStream.range(0, addressesCount).forEachOrdered(i -> {

                KeyHolder keyHolder = new GeneratedKeyHolder();

                this.template.update(
                        connection -> {

                            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                            ps.setString(1, model.getPersonName());
                            ps.setString(2, model.getPersonSurname());
                            ps.setLong(3, model.getUser().getId());
                            ps.setLong(4, addresses.get(i).getId());
                            return ps;

                        }, keyHolder);
                model.setId(keyHolder.getKey().longValue());
            });

        }

        if(addressesCount == 1){

            KeyHolder keyHolder = new GeneratedKeyHolder();

            this.template.update(
                    connection -> {

                        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                        ps.setString(1, model.getPersonName());
                        ps.setString(2, model.getPersonSurname());
                        ps.setLong(3, model.getUser().getId());
                        ps.setLong(4, model.getAddresses().get(0).getId());
                        return ps;

                    }, keyHolder);
            model.setId(keyHolder.getKey().longValue());

        }

    }

    @Override
    public Profile find(Long id) {

        Profile result = this.template.query(FIND_BY_ID_QUERY, new Long[]{id}, this.profileRowMapper).get(0);

        this.profiles.clear();

        return result;

    }

    @Override
    public void delete(Long id) {

        this.template.update(DELETE_QUERY, id);
        this.profiles.clear();

        UserDAOInterface userDAO = new UserDAOImpl(this.datasource);

        User user = userDAO.find(id);

        userDAO.delete(user.getId());

        AddressToUserDAOInterface profileInfoDAO = new AddressToUserDAOImpl(this.datasource);
        List<AddressToUser> profileInfos = profileInfoDAO.findByUser(user);
        int count = profileInfos.size();

        IntStream.range(0, count).forEachOrdered(i -> profileInfoDAO.delete(profileInfos.get(i).getId()));

    }

    @Override
    public void update(Profile model) {

        this.template.update(UPDATE_QUERY, new String[]{model.getPersonName()},
                                           new String[]{model.getPersonSurname()},
                                           new Long[]{model.getUser().getId()},
                                           new Long[]{model.getAddresses().get(0).getId()});

        UserDAOInterface userDAO = new UserDAOImpl(this.datasource);
        AddressToUserDAOInterface profileInfoDAO = new AddressToUserDAOImpl(this.datasource);

        userDAO.update(model.getUser());
        profileInfoDAO.update(model.getAddresses().get(0));

    }

    @Override
    public Profile findByUser(User user) {

        Profile result =  this.template.query(FIND_BY_USER_QUERY, new Long[]{user.getId()}, this.profileRowMapper).get(0);

        this.profiles.clear();

        return result;

    }
}
