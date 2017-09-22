package com.romanidze.jizzyshop.controllers;

import com.romanidze.jizzyshop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());

        String email = req.getParameter("email");
        String country = req.getParameter("countries");

        context.setVariable("email", email);
        context.setVariable("country", country);

        try {

            engine.process("profile.html", context, resp.getWriter());

        } catch (IOException e) {
            System.err.println("Неправильно указан роутинг");
        }

    }

}
