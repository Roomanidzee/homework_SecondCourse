package com.romanidze.perpenanto.services.interfaces;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserServiceInterface {

    void loginUser(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context);
    void registerUser(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context);
    void showUsers(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context);

}
