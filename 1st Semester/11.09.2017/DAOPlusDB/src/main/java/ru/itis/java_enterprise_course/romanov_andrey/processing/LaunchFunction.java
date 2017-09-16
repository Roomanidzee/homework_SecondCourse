package ru.itis.java_enterprise_course.romanov_andrey.processing;

import ru.itis.java_enterprise_course.romanov_andrey.dao.implementations.HumanDAOJDBCImpl;
import ru.itis.java_enterprise_course.romanov_andrey.dao.interfaces.HumanDAOInterface;
import ru.itis.java_enterprise_course.romanov_andrey.models.DBConnection;
import ru.itis.java_enterprise_course.romanov_andrey.models.Human;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class LaunchFunction {

    private Long inputID(){

        Scanner sc3 = new Scanner(System.in);

        System.out.print("id: ");
        Long id = sc3.nextLong();

        return id;

    }

    public void start(){

        Scanner sc = new Scanner(System.in);
        DBConnection dbConfig = new DBConnection();

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConfig.getDBConfig());

        boolean exit = false;

        System.out.println("Доступные команды: ");
        System.out.println("1. insert");
        System.out.println("2. find by id");
        System.out.println("3. show all");
        System.out.println("4. delete by id");
        System.out.println("5. update with id");
        System.out.println("6. exit");

        String command = null;

        try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                          configMap.get("db_password")))
        {

            Class.forName("org.postgresql.Driver");

            HumanDAOInterface humanDAO = new HumanDAOJDBCImpl(conn);

            while(!exit){

                System.out.print("Ваша команда: ");
                command = sc.nextLine();

                System.out.println();

                switch(command){

                    case "exit":

                        System.out.println("Спасибо за использование данной программы!");
                        exit = true;
                        break;

                    case "insert":

                        Scanner sc1 = new Scanner(System.in);
                        Scanner sc2 = new Scanner(System.in);

                        System.out.print("Имя: ");
                        String name = sc1.nextLine();

                        System.out.print("Фамилия: ");
                        String surname = sc2.nextLine();

                        humanDAO.save(new Human(name, surname));

                        break;

                    case "find by id":

                        Long id = this.inputID();

                        System.out.println(humanDAO.find(id));

                        break;

                    case "show all":

                        List<Human> humanList = new ArrayList<>();
                        humanList.addAll(humanDAO.findAll());

                        for (Human eachHuman : humanList) {

                            System.out.println(eachHuman);

                        }

                        break;

                    case "delete by id":

                        Long deleteID = this.inputID();

                        humanDAO.delete(deleteID);

                        break;

                    case "update with id":

                        Long updateID = this.inputID();

                        Scanner sc4 = new Scanner(System.in);
                        Scanner sc5 = new Scanner(System.in);

                        System.out.print("Имя: ");
                        String updateName = sc4.nextLine();

                        System.out.print("Фамилия: ");
                        String updateSurname = sc5.nextLine();

                        humanDAO.update(new Human(updateID, updateName, updateSurname));

                        break;

                    default:
                        System.out.println("Вы ввели неверную команду. Попробуйте заново");
                        break;
                }

            }

        }catch(SQLException e){

            throw new IllegalArgumentException(e);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
