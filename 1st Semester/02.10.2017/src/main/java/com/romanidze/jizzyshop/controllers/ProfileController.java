package com.romanidze.jizzyshop.controllers;

import com.romanidze.jizzyshop.config.TemplateEngineUtil;
import com.romanidze.jizzyshop.dao.implementations.ProfileDAOImpl;
import com.romanidze.jizzyshop.dao.implementations.UserDAOImpl;
import com.romanidze.jizzyshop.dao.interfaces.ProfileDAOInterface;
import com.romanidze.jizzyshop.dao.interfaces.UserDAOInterface;
import com.romanidze.jizzyshop.models.Profile;
import com.romanidze.jizzyshop.models.User;
import com.romanidze.jizzyshop.utils.DBConnection;
import com.romanidze.jizzyshop.utils.ProfileUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(
        name = "ProfileController",
        description = "Профиль пользователя",
        urlPatterns = {"/profile"}
)
public class ProfileController extends HttpServlet {

    @Override
    public void init(){

        try{

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        ProfileUtils utils = new ProfileUtils();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String country = req.getParameter("countries");
        boolean gender = req.getParameter("gender").equals("1");
        boolean subscription = req.getParameter("mycheckbox").equals("1");

        boolean check = !email.isEmpty() && !country.isEmpty();

        if (check) {

            boolean emailCheck = utils.checkEmail(email);

            if (emailCheck) {

                String message1 = "Введите корректный адрес электронной почты";

                context.setVariable("message", message1);

                try {
                    engine.process("registration.html", context, resp.getWriter());

                    resp.sendRedirect("/registration");
                } catch (IOException e) {
                    System.err.println("Неправильно указан роутинг");
                }

            }

            boolean countryCheck = utils.checkCountry(country);

            if (countryCheck) {

                String message2 = "Выберите страну из выпадающего списка";

                context.setVariable("message", message2);

                try {
                    engine.process("registration.html", context, resp.getWriter());

                    resp.sendRedirect("/registration");
                } catch (IOException e) {
                    System.err.println("Неправильно указан роутинг");
                }

            }

            DBConnection dbConnection = new DBConnection(getServletContext()
                    .getResourceAsStream("/WEB-INF/properties/db.properties"));

            Map<String, String> configMap = new LinkedHashMap<>();
            configMap.putAll(dbConnection.getDBConfig());

            String personGender = gender ? "Мужской" : "Женский";
            String personSubscription = subscription ? "Активна" : "Отключена";

            try (Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                               configMap.get("db_password"))) {

                UserDAOInterface userDAO = new UserDAOImpl(conn);
                ProfileDAOInterface profileDAO = new ProfileDAOImpl(conn);

                User user = User.builder()
                                .emailOrUsername(email)
                                .password(password)
                                .build();

                userDAO.save(user);

                Profile profile = Profile.builder()
                                         .country(country)
                                         .gender(personGender)
                                         .subscription(personSubscription)
                                         .userId(user.getId())
                                         .build();

                profileDAO.save(profile);

            } catch (SQLException e) {

                e.printStackTrace();

            }

            context.setVariable("email", email);
            context.setVariable("country",country);
            context.setVariable("gender", personGender);
            context.setVariable("subscription", personSubscription);

            try {

                engine.process("profile.html", context, resp.getWriter());

            } catch (IOException  e) {
                e.printStackTrace();
            }

        }else{

            String message3 = "Заполните все поля";

            context.setVariable("message", message3);

            try {
                engine.process("registration.html", context, resp.getWriter());

                resp.sendRedirect("/registration");
            } catch (IOException e) {
                System.err.println("Неправильно указан роутинг");
            }

        }

    }

}
