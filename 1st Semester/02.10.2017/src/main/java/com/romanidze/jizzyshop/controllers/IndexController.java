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
        name = "IndexController",
        description = "Стартовая страница",
        urlPatterns = {"/index"}
)
public class IndexController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("price1", "1500&#8381");
        context.setVariable("price2", "4000&#8381");
        context.setVariable("price3", "300&#8381");

        try{

            engine.process("index.html", context, resp.getWriter());

            resp.sendRedirect("/register");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
