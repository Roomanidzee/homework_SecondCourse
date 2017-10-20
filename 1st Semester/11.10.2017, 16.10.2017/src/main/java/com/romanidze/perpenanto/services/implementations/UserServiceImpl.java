package com.romanidze.perpenanto.services.implementations;

import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.UserDAOInterface;
import com.romanidze.perpenanto.models.Profile;
import com.romanidze.perpenanto.models.TempProfile;
import com.romanidze.perpenanto.models.User;
import com.romanidze.perpenanto.services.interfaces.UserServiceInterface;
import com.romanidze.perpenanto.utils.CompareAttributes;
import com.romanidze.perpenanto.utils.WorkWithCookie;
import com.romanidze.perpenanto.utils.ProfileUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UserServiceImpl implements UserServiceInterface{

    private UserDAOInterface userDAO;
    private ProfileDAOInterface profileDAO;

    private UserServiceImpl(){}

    public UserServiceImpl(UserDAOInterface userDAO, ProfileDAOInterface profileDAO){

        this.userDAO = userDAO;
        this.profileDAO = profileDAO;

    }

    @Override
    public void loginUser(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context) {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean remember = req.getParameter("remember_me").equals("1");

        HttpSession session = req.getSession(true);

        WorkWithCookie cookieWork = new WorkWithCookie();

        cookieWork.createRememberCookie(resp, remember);

        User user = userDAO.findByUsername(email);
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
        ProfileServiceImpl profileService = new ProfileServiceImpl(this.profileDAO);

        String email = req.getParameter("email");
        String plainPass = req.getParameter("password");
        String country = req.getParameter("countries");
        boolean gender = req.getParameter("gender").equals("1");
        boolean subscription = req.getParameter("mychekbox").equals("1");

        String personGender = gender ? "Мужской" : "Женский";
        String personSubscription = subscription ? "Активна" : "Отключена";

        boolean checkIfEmpty = !email.isEmpty() && !plainPass.isEmpty()
                                                && !personGender.isEmpty()
                                                && !personSubscription.isEmpty();

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

            Profile profile = Profile.builder()
                                     .country(country)
                                     .gender(personGender)
                                     .subscription(personSubscription)
                                     .userId(user.getId())
                                     .build();

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

        List<TempProfile> tempProfilesResult = new ArrayList<>();
        List<TempProfile> currentProfilesResult = new ArrayList<>();

        List<User> users = this.userDAO.findAll();
        List<Profile> profiles = this.profileDAO.findAll();

        if(users.size() != profiles.size()){

            throw new IllegalArgumentException("В базе данных нарушена целостность!");

        }

        int counter = users.size();

        IntStream.range(0, counter).forEachOrdered(i -> {

            TempProfile profile = new TempProfile();

            profile.setProfileId(users.get(i).getId());
            profile.setEmail(users.get(i).getEmailOrUsername());
            profile.setCountry(profiles.get(i).getCountry());
            profile.setSubscription(profile.getSubscription());

            tempProfilesResult.add(profile);
        });

        WorkWithCookie cookieWork = new WorkWithCookie();
        String sortType = cookieWork.getCookieWithType(req, resp);

        CompareAttributes compareAttr = new CompareAttributes();
        currentProfilesResult.addAll(compareAttr.sortList(tempProfilesResult, sortType));
        context.setVariable("profiles", currentProfilesResult);

        try{
            engine.process("show_users.html", context, resp.getWriter());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
