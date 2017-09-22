package com.romanidze.jizzyshop.controllers;

import com.romanidze.jizzyshop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("meeting", "Введите ваши данные для регистрации: ");

        try {
            engine.process("entry_page.html", context, resp.getWriter());

            resp.sendRedirect("/profile");
        } catch (IOException e) {
            System.err.println("Неправильно указан роутинг");
        }

    }
}
