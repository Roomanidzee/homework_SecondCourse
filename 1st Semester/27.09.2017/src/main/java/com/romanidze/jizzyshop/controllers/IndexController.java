package com.romanidze.jizzyshop.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        try {
            req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }


}
