package com.romanidze.perpenanto.controllers;

import com.romanidze.perpenanto.config.TemplateEngineUtil;
import com.romanidze.perpenanto.dao.implementations.ProfileDAOImpl;
import com.romanidze.perpenanto.dao.implementations.UserDAOImpl;
import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.UserDAOInterface;
import com.romanidze.perpenanto.models.Profile;
import com.romanidze.perpenanto.models.User;
import com.romanidze.perpenanto.utils.data_work.DBConnection;
import com.romanidze.perpenanto.utils.data_work.WorkWithCookie;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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
    public void init() {

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());


        DBConnection dbConnection = new DBConnection(getServletContext()
                                                   .getResourceAsStream("/WEB-INF/properties/db.properties"));

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConnection.getDBConfig());

        String email = "";
        String country = "";
        String personGender = "";
        String personSubscription = "";

        try (Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                           configMap.get("db_password"))) {

            UserDAOInterface userDAO = new UserDAOImpl(conn);
            ProfileDAOInterface profileDAO = new ProfileDAOImpl(conn);

            HttpSession session = req.getSession(true);
            WorkWithCookie cookieWork = new WorkWithCookie();

            Long userId;

            Cookie cookie1 = cookieWork.getCookieByName(req, "remember_me");

            if(cookie1.getValue().equals("no")){

                userId = (Long) session.getAttribute("user_id");

            }else{

                Cookie cookie2 = cookieWork.getCookieByName(req, "remember_user");

                Long userIdFromSession = (Long) session.getAttribute("user_id");

                if(BCrypt.checkpw(String.valueOf(userIdFromSession), cookie2.getValue())){

                    userId = (Long) session.getAttribute("user_id");

                }else{

                    throw new NullPointerException("Такого пользователя нет!");

                }
            }

            User user = userDAO.find(userId);
            Profile profile = profileDAO.findByUser(user);

            email = user.getEmailOrUsername();
            country = profile.getCountry();
            personGender = profile.getGender();
            personSubscription = profile.getSubscription();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        context.setVariable("email", email);
        context.setVariable("country", country);
        context.setVariable("gender", personGender);
        context.setVariable("subscription", personSubscription);

        try {

            engine.process("profile.html", context, resp.getWriter());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
