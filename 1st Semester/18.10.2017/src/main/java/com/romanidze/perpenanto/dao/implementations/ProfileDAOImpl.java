package com.romanidze.perpenanto.dao.implementations;

import com.google.common.collect.Lists;
import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.ProfileInfoDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.UserDAOInterface;
import com.romanidze.perpenanto.models.Profile;
import com.romanidze.perpenanto.models.ProfileInfo;
import com.romanidze.perpenanto.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ProfileDAOImpl implements ProfileDAOInterface{

    private Connection conn;
    private UserDAOInterface userDAO;
    private ProfileInfoDAOInterface profileInfoDAO;

    private static final String INSERT_QUERY = "INSERT INTO profile (user_id, person_name, person_surname, address_id)" +
                                                " VALUES (?, ?, ?, ?)";
    private static final String FIND_QUERY = "SELECT FROM profile WHERE profile.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM profile";
    private static final String DELETE_QUERY = "DELETE FROM profile WHERE profile.id = ?";
    private static final String UPDATE_QUERY =
            "UPDATE profile SET (user_id, person_name, person_surname, address_id) = (?, ?, ?, ?) " +
                    "WHERE profile.id = ?";
    private static final String FIND_BY_USER_QUERY = "SELECT FROM profile WHERE profile.user_id = ?";

    private ProfileDAOImpl(){}

    public ProfileDAOImpl(Connection conn){

        this.conn = conn;
        this.userDAO = new UserDAOImpl(conn);
        this.profileInfoDAO = new ProfileInfoDAOImpl(conn);

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
                                        .userId(rs.getLong("user_id"))
                                        .personName(rs.getString("person_name"))
                                        .personSurname(rs.getString("person_surname"))
                                        .profileInfos(Lists.newArrayList())
                                        .build();

               ProfileInfo profileInfo = this.profileInfoDAO.find(profile.getId());
               profile.getProfileInfos().add(profileInfo);

               resultList.add(profile);

           }

        }catch(SQLException e){

            e.printStackTrace();

        }

        return resultList;

    }

    @Override
    public void save(Profile model) {

        int count = model.getProfileInfos().size();

        if(count > 1){

            List<ProfileInfo> profileInfos = model.getProfileInfos();

            IntStream.range(0, count).forEachOrdered(i -> {
                try (PreparedStatement ps = this.conn.prepareStatement(INSERT_QUERY, new String[]{"id"})) {

                    ps.setLong(1, model.getUserId());
                    ps.setString(2, model.getPersonName());
                    ps.setString(3, model.getPersonSurname());
                    ps.setLong(4, profileInfos.get(i).getId());

                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {

                        if (rs.next()) {

                            Long id = rs.getLong(1);
                            model.setId(id);

                        }

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        }else{

            try(PreparedStatement ps = this.conn.prepareStatement(INSERT_QUERY, new String[]{"id"})){

                ps.setLong(1, model.getUserId());
                ps.setString(2, model.getPersonName());
                ps.setString(3, model.getPersonSurname());
                ps.setLong(4, model.getProfileInfos().get(0).getId());

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
                                     .userId(rs.getLong("user_id"))
                                     .personName(rs.getString("person_name"))
                                     .personSurname(rs.getString("person_surname"))
                                     .profileInfos(Lists.newArrayList())
                                     .build();

                    ProfileInfo profileInfo = this.profileInfoDAO.find(profile.getId());
                    profile.getProfileInfos().add(profileInfo);

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

            this.userDAO.delete(id);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Profile model) {

        try(PreparedStatement ps = this.conn.prepareStatement(UPDATE_QUERY)){

            ps.setLong(1, model.getUserId());
            ps.setString(2, model.getPersonName());
            ps.setString(3, model.getPersonSurname());
            ps.setLong(4, model.getProfileInfos().get(0).getId());
            ps.setLong(5, model.getId());

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
                                     .userId(rs.getLong("user_id"))
                                     .personName(rs.getString("person_name"))
                                     .personSurname(rs.getString("person_surname"))
                                     .profileInfos(Lists.newArrayList())
                                     .build();

                    ProfileInfo profileInfo = this.profileInfoDAO.find(profile.getId());
                    profile.getProfileInfos().add(profileInfo);

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
