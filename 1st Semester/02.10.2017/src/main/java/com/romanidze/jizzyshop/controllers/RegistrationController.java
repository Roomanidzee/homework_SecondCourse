package com.romanidze.jizzyshop.controllers;

import com.romanidze.jizzyshop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "RegistrationController",
        description = "Страница регистрации",
        urlPatterns = {"/registration"}
)
public class RegistrationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("meeting", "Введите ваши данные для регистрации: ");

        try {
            engine.process("registration.html", context, resp.getWriter());

            resp.sendRedirect("/profile");
        } catch (IOException e) {
            System.err.println("Неправильно указан роутинг");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());

        try {
            engine.process("registration.html", context, resp.getWriter());
        } catch (IOException e) {
            System.err.println("Неправильно указан роутинг");
        }

    }
}