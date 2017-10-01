package com.romanidze.jizzyshop.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        try {
            req.getRequestDispatcher("/WEB-INF/views/entry_page.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

            try {
                req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }

    }
}
