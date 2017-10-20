package com.romanidze.perpenanto.services.implementations;

import com.google.common.collect.Lists;
import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.ProfileInfoDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.UserDAOInterface;
import com.romanidze.perpenanto.models.Profile;
import com.romanidze.perpenanto.models.ProfileInfo;
import com.romanidze.perpenanto.models.User;
import com.romanidze.perpenanto.services.interfaces.UserServiceInterface;
import com.romanidze.perpenanto.utils.WorkWithCookie;
import com.romanidze.perpenanto.utils.ProfileUtils;
import com.romanidze.perpenanto.utils.comparators.CompareAttributes;
import com.romanidze.perpenanto.utils.comparators.CompareUserAttributes;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserServiceInterface{

    private UserDAOInterface userDAO;
    private ProfileDAOInterface profileDAO;
    private ProfileInfoDAOInterface profileInfoDAO;

    private UserServiceImpl(){}

    public UserServiceImpl(UserDAOInterface userDAO, ProfileDAOInterface profileDAO, ProfileInfoDAOInterface profileInfoDAO){

        this.userDAO = userDAO;
        this.profileDAO = profileDAO;
        this.profileInfoDAO = profileInfoDAO;

    }

    @Override
    public void loginUser(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context) {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean remember = req.getParameter("remember_me").equals("1");

        HttpSession session = req.getSession(true);

        WorkWithCookie cookieWork = new WorkWithCookie();

        cookieWork.createRememberCookie(resp, remember);

        User user = this.userDAO.findByUsername(email);
        String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt());

        if(remember){

            cookieWork.createCookie(resp, "remember_user",
                    BCrypt.hashpw(String.valueOf(user.getId()), BCrypt.gensalt()));

        }

        if(BCrypt.checkpw(user.getPassword(), hashedPass)){

            session.setAttribute("user_id", user.getId());
            try {
                resp.sendRedirect("/profile");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{

            context.setVariable("message", "Вы неправильно ввели логин, или пароль. Введите заново");

            try {
                engine.process("login.html", context, resp.getWriter());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void registerUser(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context){

        ProfileUtils utils = new ProfileUtils();
        ProfileServiceImpl profileService = new ProfileServiceImpl(this.profileDAO, this.userDAO);

        String email = req.getParameter("email");
        String plainPass = req.getParameter("password");
        String country = req.getParameter("countries");
        String personName = req.getParameter("person_name");
        String personSurname = req.getParameter("person_surname");
        int postalCode = Integer.valueOf(req.getParameter("post_index"));
        String city = req.getParameter("city");
        String street = req.getParameter("street");
        int homeNumber = Integer.valueOf(req.getParameter("home_number"));

        boolean checkIfEmpty = !email.isEmpty() && !plainPass.isEmpty()
                                                && !country.isEmpty()
                                                && !personName.isEmpty()
                                                && !personSurname.isEmpty()
                                                && !(postalCode == 0)
                                                && !city.isEmpty()
                                                && !street.isEmpty()
                                                && !(homeNumber == 0);

        if(!checkIfEmpty){

            boolean emailCheck = utils.checkEmail(email);

            if(emailCheck){

                String message1 = "Введите корректный адрес электронной почты";

                context.setVariable("message", message1);

                try {
                    engine.process("login.html", context, resp.getWriter());

                    resp.sendRedirect("/login");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            boolean countryCheck = utils.checkCountry(country);

            if(countryCheck){

                String message2 = "Выберите страну из выпадающего списка";

                context.setVariable("message", message2);

                try {
                    engine.process("login.html", context, resp.getWriter());

                    resp.sendRedirect("/login");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            User user = User.builder()
                            .emailOrUsername(email)
                            .password(BCrypt.hashpw(plainPass, BCrypt.gensalt()))
                            .build();

            this.userDAO.save(user);

            ProfileInfo profileInfo = ProfileInfo.builder()
                                                 .userId(user.getId())
                                                 .country(country)
                                                 .postalCode(postalCode)
                                                 .city(city)
                                                 .street(street)
                                                 .homeNumber(homeNumber)
                                                 .build();

            this.profileInfoDAO.save(profileInfo);

            Profile profile = Profile.builder()
                                     .userId(user.getId())
                                     .personName(personName)
                                     .personSurname(personSurname)
                                     .profileInfos(Lists.newArrayList())
                                     .build();

            profile.getProfileInfos().add(profileInfo);

            profileService.addProfile(profile);

            Long userId = user.getId();

            HttpSession session = req.getSession(true);
            session.setAttribute("user_id", userId);

            try {
                resp.sendRedirect("/user/profile");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{

            context.setVariable("message", "Заполните все поля");

            try {
                engine.process("login.html", context, resp.getWriter());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void showUsers(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context) {

        List<User> currentUsersResult = new ArrayList<>();

        List<User> users = this.userDAO.findAll();

        WorkWithCookie cookieWork = new WorkWithCookie();
        Cookie cookie = cookieWork.getCookieByName(req, "status");

        CompareAttributes<User> compareAttr = new CompareUserAttributes();
        currentUsersResult.addAll(compareAttr.sortByAttr(users, cookie.getValue()));

        context.setVariable("users", currentUsersResult);

        try{
            engine.process("show_users.html", context, resp.getWriter());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
