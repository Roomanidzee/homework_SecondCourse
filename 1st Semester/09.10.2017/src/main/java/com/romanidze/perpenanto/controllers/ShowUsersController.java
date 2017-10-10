package com.romanidze.perpenanto.controllers;

import com.romanidze.perpenanto.config.TemplateEngineUtil;
import com.romanidze.perpenanto.dao.implementations.ProfileDAOImpl;
import com.romanidze.perpenanto.dao.implementations.UserDAOImpl;
import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.UserDAOInterface;
import com.romanidze.perpenanto.models.Profile;
import com.romanidze.perpenanto.models.TempProfile;
import com.romanidze.perpenanto.models.User;
import com.romanidze.perpenanto.utils.data_work.CompareAttributes;
import com.romanidze.perpenanto.utils.data_work.DBConnection;
import com.romanidze.perpenanto.utils.data_work.WorkWithCookie;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@WebServlet(
        name = "ShowUsersController",
        description = "Пользователи сервиса",
        urlPatterns = {"/admin/users"}
)
public class ShowUsersController extends HttpServlet{

    @Override
    public void init(){

        try{

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        DBConnection dbConnection = new DBConnection(getServletContext()
                                                      .getResourceAsStream("/WEB-INF/properties/db.properties"));

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConnection.getDBConfig());

        List<TempProfile> tempProfilesResult = new ArrayList<>();
        List<TempProfile> currentProfilesResult = new ArrayList<>();
        ProfileDAOInterface profileDAO = null;
        UserDAOInterface userDAO = null;

        try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                          configMap.get("db_password")))
        {

            profileDAO = new ProfileDAOImpl(conn);
            userDAO = new UserDAOImpl(conn);

            List<User> users = userDAO.findAll();
            List<Profile> profiles = profileDAO.findAll();

            if(users.size() != profiles.size()){

                throw new IllegalArgumentException("В базе данных нарушена целостность!");

            }

            int counter = users.size();

            IntStream.range(0, counter).forEachOrdered(i -> {

                TempProfile profile = new TempProfile();

                profile.setProfileId(users.get(i).getId());
                profile.setEmail(users.get(i).getEmailOrUsername());
                profile.setCountry(profiles.get(i).getCountry());
                profile.setSubscription(profile.getSubscription());

                tempProfilesResult.add(profile);
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        WorkWithCookie cookieWork = new WorkWithCookie();
        String sortType = cookieWork.getCookieWithType(req, resp);

        CompareAttributes compareAttr = new CompareAttributes();
        currentProfilesResult.addAll(compareAttr.sortList(tempProfilesResult, sortType));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("profiles", currentProfilesResult);

        try{

            engine.process("show_users.html", context, resp.getWriter());

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
