package com.romanidze.perpenanto.dao.implementations;

import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.models.Profile;
import com.romanidze.perpenanto.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileDAOImpl implements ProfileDAOInterface{

    private Connection conn;

    private static final String INSERT_QUERY = "INSERT INTO profile (country, gender, subscription, user_id)" +
                                                " VALUES (?, ?, ?, ?)";
    private static final String FIND_QUERY = "SELECT FROM profile WHERE profile.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM profile";
    private static final String DELETE_QUERY = "DELETE FROM profile WHERE profile.id = ?";
    private static final String UPDATE_QUERY =
            "UPDATE profile SET (country, gender, subscription, user_id) = (?, ?, ?, ?) " +
                    "WHERE profile.id = ?";
    private static final String FIND_BY_USER_QUERY = "SELECT FROM profile WHERE profile.user_id = ?";
    private static final String FIND_BY_COUNTRY_QUERY = "SELECT FROM profile WHERE profile.country = ?";

    private ProfileDAOImpl(){}

    public ProfileDAOImpl(Connection conn){

        this.conn = conn;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Profile> findAll() {

        List<Profile> resultList = new ArrayList<>();

        try(PreparedStatement ps = this.conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = ps.executeQuery())
        {

           while(rs.next()){

               Profile profile = Profile.builder()
                                        .id(rs.getLong("id"))
                                        .country(rs.getString("country"))
                                        .gender(rs.getString("gender"))
                                        .subscription(rs.getString("subscription"))
                                        .userId(rs.getLong("user_id"))
                                        .build();

               resultList.add(profile);

           }

        }catch(SQLException e){

            e.printStackTrace();

        }

        return resultList;

    }

    @Override
    public void save(Profile model) {

        try(PreparedStatement ps = this.conn.prepareStatement(INSERT_QUERY, new String[]{"id"})){

            ps.setString(1, model.getCountry());
            ps.setString(2, model.getGender());
            ps.setString(3, model.getSubscription());
            ps.setLong(4, model.getUserId());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){

                if(rs.next()){

                    Long id = rs.getLong(1);
                    model.setId(id);

                }

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Profile find(Long id) {

        Profile profile = null;

        try(PreparedStatement ps = this.conn.prepareStatement(FIND_QUERY)){

            ps.setLong(1, id);

            try(ResultSet rs = ps.executeQuery()){

                if(rs.next()){

                    profile = Profile.builder()
                                     .id(rs.getLong("id"))
                                     .country(rs.getString("country"))
                                     .gender(rs.getString("gender"))
                                     .subscription(rs.getString("subscription"))
                                     .userId(rs.getLong("user_id"))
                                     .build();

                }else{
                    throw new IllegalArgumentException("Profile not found");
                }

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return profile;

    }

    @Override
    public void delete(Long id) {

        try(PreparedStatement ps = this.conn.prepareStatement(DELETE_QUERY)){

            ps.setLong(1, id);
            ps.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Profile model) {

        try(PreparedStatement ps = this.conn.prepareStatement(UPDATE_QUERY)){

            ps.setString(1, model.getCountry());
            ps.setString(2, model.getGender());
            ps.setString(3, model.getSubscription());
            ps.setLong(4, model.getUserId());

            ps.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Profile findByUser(User user) {

        Profile profile = null;

        try(PreparedStatement ps = this.conn.prepareStatement(FIND_BY_USER_QUERY)){

            ps.setLong(1, user.getId());

            try(ResultSet rs = ps.executeQuery()){

                if(rs.next()){

                    profile = Profile.builder()
                                     .id(rs.getLong("id"))
                                     .country(rs.getString("country"))
                                     .gender(rs.getString("gender"))
                                     .subscription(rs.getString("subscription"))
                                     .userId(rs.getLong("user_id"))
                                     .build();

                }else{
                    throw new IllegalArgumentException("Profile not found");
                }

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return profile;

    }

    @Override
    public Profile findByCountry(String country) {

        Profile profile = null;

        try(PreparedStatement ps = this.conn.prepareStatement(FIND_BY_COUNTRY_QUERY)){

            ps.setString(1, country);

            try(ResultSet rs = ps.executeQuery()){

                if(rs.next()){

                    profile = Profile.builder()
                            .id(rs.getLong("id"))
                            .country(rs.getString("country"))
                            .gender(rs.getString("gender"))
                            .subscription(rs.getString("subscription"))
                            .userId(rs.getLong("user_id"))
                            .build();

                }else{
                    throw new IllegalArgumentException("Profile not found");
                }

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return profile;

    }
}
