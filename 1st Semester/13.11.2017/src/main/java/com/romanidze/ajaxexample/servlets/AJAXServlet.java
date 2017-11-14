package com.romanidze.ajaxexample.servlets;

import com.romanidze.ajaxexample.config.TemplateEngineUtil;
import com.romanidze.ajaxexample.workwithfiles.FileWork;
import org.json.simple.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 14.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@WebServlet(
        name = "AJAX Example",
        description = "Пример использования AJAX",
        urlPatterns = {"/ajax_example"}
)
public class AJAXServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        JSONObject jsonObject = new JSONObject();
        FileWork fileWork = null;

        String parameter = req.getParameter("countriesType");

        switch(parameter){

            case "Россия":
                fileWork = new FileWork(req.getServletContext(), "/WEB-INF/russian_cars.txt");
                jsonObject = fileWork.getJSON();
                break;

            case "Германия":
                fileWork = new FileWork(req.getServletContext(), "/WEB-INF/german_cars.txt");
                jsonObject = fileWork.getJSON();
                break;

        }

        resp.setContentType("application/json");

        try {
            PrintWriter pw = resp.getWriter();
            pw.write(jsonObject.toJSONString());

            engine.process("ajax_example.html", context, pw);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
