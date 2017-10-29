package com.romanidze.perpenanto.services.interfaces;

import com.romanidze.perpenanto.models.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserServiceInterface {

    void loginUser(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context);
    void registerUser(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context);

    List<User> getUsers();
    List<User> getUsersByCookie(HttpServletRequest req, HttpServletResponse resp);
}
