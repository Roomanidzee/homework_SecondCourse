package com.romanidze.perpenanto.controllers;

import com.romanidze.perpenanto.config.TemplateEngineUtil;
import com.romanidze.perpenanto.dao.implementations.ProfileDAOImpl;
import com.romanidze.perpenanto.dao.implementations.ProfileInfoDAOImpl;
import com.romanidze.perpenanto.dao.implementations.UserDAOImpl;
import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.ProfileInfoDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.UserDAOInterface;
import com.romanidze.perpenanto.services.implementations.UserServiceImpl;
import com.romanidze.perpenanto.utils.DBConnection;
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
        name = "LoginController",
        description = "Страница входа",
        urlPatterns = {"/login"}
)
public class LoginController extends HttpServlet{

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

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        try{
            engine.process("login.html", context, resp.getWriter());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        DBConnection dbConnection = new DBConnection(getServletContext()
                                                      .getResourceAsStream("/WEB-INF/properties/db.properties"));

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConnection.getDBConfig());

        try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                          configMap.get("db_password")))
        {

            UserDAOInterface userDAO = new UserDAOImpl(conn);
            ProfileDAOInterface profileDAO = new ProfileDAOImpl(conn);
            ProfileInfoDAOInterface profileInfoDAO = new ProfileInfoDAOImpl(conn);
            UserServiceImpl userService = new UserServiceImpl(userDAO, profileDAO, profileInfoDAO);

            if(req.getParameter("form_action").equals("login")){
                userService.loginUser(req, resp, engine, context);
            }
            if(req.getParameter("form_action").equals("register")){
                userService.registerUser(req, resp, engine, context);
            }

        }catch (SQLException  e) {
            e.printStackTrace();
        }

    }

}
