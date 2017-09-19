package com.romanidze.firstServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirstServlet extends HttpServlet{

    private void process(HttpServletRequest req, HttpServletResponse resp){

        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>")
          .append("<html lang = \"ru\">\n")
          .append("<head>\n")
          .append("<meta charset=\"UTF-8\">\n")
          .append("</head>\n")
          .append("<body>\n")
          .append("<h2>Welcome, first user of Servlet!</h2>\n")
          .append("</body>\n")
          .append("</html>");

        resp.setStatus(200);

        try {
            resp.getWriter().write(sb.toString());
        } catch (IOException e) {
           System.err.println("Произошла ошибка во время отправки ответа на запрос");
        }

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        this.process(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        this.process(req, resp);
    }

}
