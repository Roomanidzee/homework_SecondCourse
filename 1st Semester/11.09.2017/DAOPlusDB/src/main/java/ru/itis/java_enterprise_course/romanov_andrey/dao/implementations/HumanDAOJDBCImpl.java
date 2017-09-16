package ru.itis.java_enterprise_course.romanov_andrey.dao.implementations;

import ru.itis.java_enterprise_course.romanov_andrey.dao.interfaces.HumanDAOInterface;
import ru.itis.java_enterprise_course.romanov_andrey.models.Human;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HumanDAOJDBCImpl implements HumanDAOInterface {

    private Connection connection;

    private static final String INSERT_QUERY = "INSERT INTO human (name, surname) VALUES (?, ?)";
    private static final String FIND_QUERY = "SELECT FROM human WHERE human.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * from human";
    private static final String DELETE_QUERY = "DELETE FROM human WHERE human.id = ?";
    private static final String UPDATE_QUERY = "UPDATE human SET(name, surname) = (?, ?) WHERE human.id = ?";

    public HumanDAOJDBCImpl(Connection con){

         this.connection = con;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void save(Human model) {

        try(PreparedStatement ps = this.connection.prepareStatement(INSERT_QUERY, new String[] {"id"})){

            Class.forName("org.postgresql.Driver");

            ps.setString(1, model.getName());
            ps.setString(2, model.getSurname());
            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){

                if(rs.next()){

                    long id = rs.getLong(1);
                    model.setId(id);

                }

            }

        }catch(SQLException e){

            throw new IllegalArgumentException(e);

        }catch(ClassNotFoundException e){

            throw new NullPointerException();

        }finally {
            System.out.println("Сохранение в базу успешно завершено.");
        }

    }

    @Override
    public Human find(Long id) {

        Human human = null;

        try(PreparedStatement ps = this.connection.prepareStatement(FIND_QUERY)){

            Class.forName("org.postgresql.Driver");

            ps.setLong(1, id);



            try(ResultSet rs = ps.executeQuery()){

                if(rs.next()){

                    human = Human.builder()
                                 .id(rs.getLong("id"))
                                 .name(rs.getString("name"))
                                 .surname(rs.getString("surname"))
                                 .build();

                }else throw new IllegalArgumentException("Human not found");

            }

        }catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return human;

    }

    @Override
    public List<Human> findAll(){

        List<Human> resultList = new ArrayList<>();

        try(PreparedStatement ps = this.connection.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = ps.executeQuery())
        {

            Class.forName("org.postgresql.Driver");

            while(rs.next()){

                Human human = Human.builder()
                                   .id(rs.getLong("id"))
                                   .name(rs.getString("name"))
                                   .surname(rs.getString("surname"))
                                   .build();

                resultList.add(human);

            }

        }catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultList;

    }

    @Override
    public void delete(Long id) {

        try(PreparedStatement ps = this.connection.prepareStatement(DELETE_QUERY)){

            Class.forName("org.postgresql.Driver");

            ps.setLong(1, id);
            ps.executeUpdate();

        }catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            System.out.println("Сущность с id: " + id + "удалена.");
        }

    }

    @Override
    public void update(Human model) {

        try(PreparedStatement ps = this.connection.prepareStatement(UPDATE_QUERY)){

            Class.forName("org.postgresql.Driver");

            ps.setString(1, model.getName());
            ps.setString(2, model.getSurname());
            ps.setLong(3, model.getId());

            ps.executeUpdate();

        }catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Сущность обновлена");
        }

    }
}
