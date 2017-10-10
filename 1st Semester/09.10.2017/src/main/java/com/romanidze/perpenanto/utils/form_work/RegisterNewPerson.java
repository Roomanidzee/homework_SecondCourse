package com.romanidze.perpenanto.utils.form_work;

import com.romanidze.perpenanto.dao.implementations.ProfileDAOImpl;
import com.romanidze.perpenanto.dao.implementations.UserDAOImpl;
import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.UserDAOInterface;
import com.romanidze.perpenanto.models.Profile;
import com.romanidze.perpenanto.models.User;
import com.romanidze.perpenanto.utils.data_work.DBConnection;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegisterNewPerson {

    public static void register(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context){

        try{

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ProfileUtils utils = new ProfileUtils();

        String email = req.getParameter("email");
        String plainPass = req.getParameter("password");
        String country = req.getParameter("countries");
        boolean gender = req.getParameter("gender").equals("1");
        boolean subscription = req.getParameter("mychekbox").equals("1");

        String personGender = gender ? "Мужской" : "Женский";
        String personSubscription = subscription ? "Активна" : "Отключена";

        boolean checkIfEmpty = !email.isEmpty() && !plainPass.isEmpty()
                                                && !personGender.isEmpty()
                                                && !personSubscription.isEmpty();

        if(!checkIfEmpty){

            boolean emailCheck = utils.checkEmail(email);

            if(emailCheck){

                String message1 = "Введите корректный адрес электронной почты";

                context.setVariable("message", message1);

                try {
                    engine.process("login.html", context, resp.getWriter());

                    resp.sendRedirect("/login");
                } catch (IOException e) {
                   e.printStackTrace();
                }

            }

            boolean countryCheck = utils.checkCountry(country);

            if(countryCheck){

                String message2 = "Выберите страну из выпадающего списка";

                context.setVariable("message", message2);

                try {
                    engine.process("login.html", context, resp.getWriter());

                    resp.sendRedirect("/login");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            DBConnection dbConnection = new DBConnection(req.getServletContext()
                                                           .getResourceAsStream("/WEB-INF/properties/db.properties"));

            Map<String, String> configMap = new LinkedHashMap<>();
            configMap.putAll(dbConnection.getDBConfig());

            User user = null;
            Long userId = null;

            try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                              configMap.get("db_password")))
            {

                UserDAOInterface userDAO = new UserDAOImpl(conn);
                ProfileDAOInterface profileDAO = new ProfileDAOImpl(conn);

                user = User.builder()
                           .emailOrUsername(email)
                           .password(BCrypt.hashpw(plainPass, BCrypt.gensalt()))
                           .build();

                userDAO.save(user);

                Profile profile = Profile.builder()
                                         .country(country)
                                         .gender(personGender)
                                         .subscription(personSubscription)
                                         .userId(user.getId())
                                         .build();

                profileDAO.save(profile);

                userId = user.getId();

            }catch (SQLException e) {

                e.printStackTrace();

            }

            HttpSession session = req.getSession(true);
            session.setAttribute("user_id", userId);

            try {
                resp.sendRedirect("/profile");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{

            context.setVariable("message", "Заполните все поля");

            try {
                engine.process("login.html", context, resp.getWriter());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
