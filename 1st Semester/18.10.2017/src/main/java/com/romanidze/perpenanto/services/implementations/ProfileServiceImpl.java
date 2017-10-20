package com.romanidze.perpenanto.services.implementations;

import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.UserDAOInterface;
import com.romanidze.perpenanto.models.Profile;
import com.romanidze.perpenanto.models.User;
import com.romanidze.perpenanto.services.interfaces.ProfileServiceInterface;
import com.romanidze.perpenanto.utils.WorkWithCookie;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileServiceImpl implements ProfileServiceInterface{

    private ProfileDAOInterface profileDAO;
    private UserDAOInterface userDAO;

    private ProfileServiceImpl(){}

    public ProfileServiceImpl(ProfileDAOInterface profileDAO, UserDAOInterface userDAO){

        this.profileDAO = profileDAO;
        this.userDAO = userDAO;

    }

    @Override
    public void addProfile(Profile model) {
        this.profileDAO.save(model);
    }

    @Override
    public void showProfile(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context) {

        HttpSession session = req.getSession(true);
        WorkWithCookie cookieWork = new WorkWithCookie();

        Long userId;

        Cookie cookie1 = cookieWork.getCookieByName(req, "remember_me");

        if(cookie1.getValue().equals("no")){

            userId = (Long) session.getAttribute("user_id");

        }else{

            Cookie cookie2 = cookieWork.getCookieByName(req, "remember_user");

            Long userIdFromSession = (Long) session.getAttribute("user_id");

            if(BCrypt.checkpw(String.valueOf(userIdFromSession), cookie2.getValue())){

                userId = (Long) session.getAttribute("user_id");

            }else{

                throw new NullPointerException("Такого пользователя нет!");

            }
        }

        User user = userDAO.find(userId);
        Profile profile = profileDAO.findByUser(user);

        String email = user.getEmailOrUsername();
        String personName = profile.getPersonName();
        String personSurname = profile.getPersonSurname();
        String country = profile.getProfileInfos().get(0).getCountry();
        int postIndex = profile.getProfileInfos().get(0).getPostalCode();
        String city = profile.getProfileInfos().get(0).getCity();
        String street = profile.getProfileInfos().get(0).getStreet();
        int homeNumber = profile.getProfileInfos().get(0).getHomeNumber();

        Map<String, Object> propertyMap = new HashMap<>();

        propertyMap.put("email", email);
        propertyMap.put("name", personName);
        propertyMap.put("surname", personSurname);
        propertyMap.put("country", country);
        propertyMap.put("postIndex", postIndex);
        propertyMap.put("city", city);
        propertyMap.put("street", street);
        propertyMap.put("homeNumber", homeNumber);

        propertyMap.forEach(context::setVariable);

        try {
            engine.process("profile.html", context, resp.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
