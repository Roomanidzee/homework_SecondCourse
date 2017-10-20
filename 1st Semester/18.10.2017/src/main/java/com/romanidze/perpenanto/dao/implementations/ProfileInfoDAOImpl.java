package com.romanidze.perpenanto.dao.implementations;

import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.ProfileInfoDAOInterface;
import com.romanidze.perpenanto.models.ProfileInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileInfoDAOImpl implements ProfileInfoDAOInterface{

    private Connection conn;
    private ProfileDAOInterface profileDAO;

    private static final String INSERT_QUERY =
            "INSERT INTO profile_info(user_id, country, postal_code, city, street, home_number) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String FIND_QUERY = "SELECT FROM profile_info WHERE profile_info.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM profile_info LEFT JOIN users ON users.id = profile_info.user_id";
    private static final String DELETE_QUERY = "DELETE FROM profile_info WHERE profile_info.id = ?";
    private static final String UPDATE_QUERY = "UPDATE profile_info " +
     "SET (user_id, country, postal_code, city, street, home_number) = (?, ?, ?, ?, ?, ?) " +
            "WHERE profile_info.id = ?";

    private ProfileInfoDAOImpl(){}

    public ProfileInfoDAOImpl(Connection conn){

        this.conn = conn;
        this.profileDAO = new ProfileDAOImpl(conn);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ProfileInfo> findAll() {

        List<ProfileInfo> resultList = new ArrayList<>();

        try(PreparedStatement ps = this.conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                ProfileInfo profileInfo = ProfileInfo.builder()
                                                     .id(rs.getLong(1))
                                                     .userId(rs.getLong(2))
                                                     .country(rs.getString(3))
                                                     .postalCode(rs.getInt(4))
                                                     .city(rs.getString(5))
                                                     .street(rs.getString(6))
                                                     .homeNumber(rs.getInt(7))
                                                     .build();

                resultList.add(profileInfo);

            }

        }catch(SQLException e){

            e.printStackTrace();

        }

        return resultList;

    }

    @Override
    public void save(ProfileInfo model) {

        try(PreparedStatement ps = this.conn.prepareStatement(INSERT_QUERY, new String[]{"id"})){

            ps.setLong(1, model.getUserId());
            ps.setString(2, model.getCountry());
            ps.setInt(3, model.getPostalCode());
            ps.setString(4, model.getCity());
            ps.setString(5, model.getStreet());
            ps.setInt(6, model.getHomeNumber());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){

                if(rs.next()){

                    Long id = rs.getLong(1);
                    model.setId(id);

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ProfileInfo find(Long id) {

        ProfileInfo profileInfo = null;

        try(PreparedStatement ps = this.conn.prepareStatement(FIND_QUERY)){

            ps.setLong(1, id);

            try(ResultSet rs = ps.executeQuery()){

                if(rs.next()){

                    profileInfo = ProfileInfo.builder()
                                             .id(rs.getLong(1))
                                             .userId(rs.getLong(2))
                                             .country(rs.getString(3))
                                             .postalCode(rs.getInt(4))
                                             .city(rs.getString(5))
                                             .street(rs.getString(6))
                                             .homeNumber(rs.getInt(7))
                                             .build();

                }else{
                    throw new IllegalArgumentException("ProfileInfo not found");
                }

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return profileInfo;
    }

    @Override
    public void delete(Long id) {

        try(PreparedStatement ps = this.conn.prepareStatement(DELETE_QUERY)){

            ps.setLong(1, id);
            ps.executeUpdate();

            this.profileDAO.delete(id);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(ProfileInfo model) {

        try(PreparedStatement ps = this.conn.prepareStatement(UPDATE_QUERY)){

            ps.setLong(1, model.getUserId());
            ps.setString(2, model.getCountry());
            ps.setInt(3, model.getPostalCode());
            ps.setString(4, model.getCity());
            ps.setString(5, model.getStreet());
            ps.setInt(6, model.getHomeNumber());
            ps.setLong(7, model.getId());

            ps.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
