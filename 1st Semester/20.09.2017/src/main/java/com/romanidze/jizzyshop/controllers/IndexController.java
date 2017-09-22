package com.romanidze.jizzyshop.controllers;

import com.romanidze.jizzyshop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("price1", "300");
        context.setVariable("price2", "4000");
        context.setVariable("price3", "1500");

        try {
            engine.process("index.html", context, resp.getWriter());

            resp.sendRedirect("/login");
        } catch (IOException e) {
            System.err.println("Неправильно указан роутинг");
        }

    }

}
