package com.romanidze.jizzyshop.controllers;

import com.romanidze.jizzyshop.utils.ProfileUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        ProfileUtils utils = new ProfileUtils();

        Set<String> countrySet = new HashSet<>();
        countrySet.addAll(utils.getCountrySet());


        String emailPattern = "/([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,})$/";

        String email = req.getParameter("email");
        String country = req.getParameter("countries");
        boolean gender = req.getParameter("gender").equals("1");
        boolean subscription = req.getParameter("mycheckbox").equals("1");

        boolean check = !email.isEmpty() && !country.isEmpty();


        if(check){

            Pattern p = Pattern.compile(emailPattern);
            Matcher m = p.matcher(email);

            if(!m.matches()){

                String message1 = "Введите корректный адрес электронной почты";

                req.setAttribute("message", message1);

                /*try {
                    req.getRequestDispatcher("/WEB-INF/views/entry_page.jsp").forward(req, resp);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }*/

            }

            int count = (int) countrySet.stream()
                                        .filter(tempCountrySet -> !tempCountrySet.equals(country))
                                        .count();

            if(count == countrySet.size()){

                String message2 = "Выберите страну из выпадающего списка";

                req.setAttribute("message", message2);

                /*try {
                    req.getRequestDispatcher("/WEB-INF/views/entry_page.jsp").forward(req, resp);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }*/

            }

            String personGender = gender ? "Мужской" : "Женский";
            String personSubscription = subscription ? "Активна" : "Отключена";

            List<String> data = new ArrayList<>();
            data.add(email);
            data.add(country);
            data.add(personGender);
            data.add(personSubscription);

            ServletContext context = getServletContext();

            File csvFile = null;
            try {
                csvFile = new File(context.getResource("/WEB-INF/info.csv").getFile());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            utils.saveData(csvFile, data);

            req.setAttribute("email", email);
            req.setAttribute("country", country);
            req.setAttribute("gender", personGender);
            req.setAttribute("subscription", personSubscription);

            try {
               this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            } catch (ServletException| IOException e) {
                e.printStackTrace();
            }

        }else{

            String message3 = "Заполните все поля";

            req.setAttribute("message", message3);

            /*try {
                req.getRequestDispatcher("/WEB-INF/views/entry_page.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }*/

        }

    }

}
