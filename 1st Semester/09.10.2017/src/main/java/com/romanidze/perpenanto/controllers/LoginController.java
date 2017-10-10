package com.romanidze.perpenanto.controllers;

import com.romanidze.perpenanto.config.TemplateEngineUtil;
import com.romanidze.perpenanto.dao.implementations.UserDAOImpl;
import com.romanidze.perpenanto.dao.interfaces.UserDAOInterface;
import com.romanidze.perpenanto.models.User;
import com.romanidze.perpenanto.utils.data_work.DBConnection;
import com.romanidze.perpenanto.utils.data_work.WorkWithCookie;
import com.romanidze.perpenanto.utils.form_work.RegisterNewPerson;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        HttpSession session = req.getSession(true);

        String email = "";
        String password = "";

        DBConnection dbConnection = new DBConnection(getServletContext()
                                                      .getResourceAsStream("/WEB-INF/properties/db.properties"));

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConnection.getDBConfig());

        try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                          configMap.get("db_password")))
        {

            UserDAOInterface userDAO = new UserDAOImpl(conn);

            if(req.getParameter("form_action").equals("login")){

                email = req.getParameter("email");
                password = req.getParameter("password");
                boolean remember = req.getParameter("remember_me").equals("1");

                WorkWithCookie cookieWork = new WorkWithCookie();

                cookieWork.createRememberCookie(resp, remember);

                User user = userDAO.findByUsername(email);
                String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt());

                if(remember){

                    cookieWork.createCookie(resp, "remember_user",
                                                BCrypt.hashpw(String.valueOf(user.getId()), BCrypt.gensalt()));

                }

                if(BCrypt.checkpw(user.getPassword(), hashedPass)){

                    session.setAttribute("user_id", user.getId());
                    resp.sendRedirect("/profile");

                }else{

                    context.setVariable("message", "Вы неправильно ввели логин, или пароль. Введите заново");

                    engine.process("login.html", context, resp.getWriter());


                }

            }
            if(req.getParameter("form_action").equals("register")){

                RegisterNewPerson.register(req, resp, engine, context);

            }

        }catch (SQLException | IOException e) {

            e.printStackTrace();

        }

    }

}
