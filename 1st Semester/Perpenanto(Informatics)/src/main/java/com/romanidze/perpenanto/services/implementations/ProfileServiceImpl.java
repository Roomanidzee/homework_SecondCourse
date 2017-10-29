package com.romanidze.perpenanto.services.implementations;

import com.romanidze.perpenanto.dao.implementations.ProfileDAOImpl;
import com.romanidze.perpenanto.dao.implementations.UserDAOImpl;
import com.romanidze.perpenanto.dao.interfaces.ProfileDAOInterface;
import com.romanidze.perpenanto.dao.interfaces.UserDAOInterface;
import com.romanidze.perpenanto.dto.implementations.ProfileTransferImpl;
import com.romanidze.perpenanto.dto.interfaces.ProfileTransferInterface;
import com.romanidze.perpenanto.models.Profile;
import com.romanidze.perpenanto.models.User;
import com.romanidze.perpenanto.models.temp.TempProfile;
import com.romanidze.perpenanto.services.interfaces.ProfileServiceInterface;
import com.romanidze.perpenanto.utils.DBConnection;
import com.romanidze.perpenanto.utils.WorkWithCookie;
import com.romanidze.perpenanto.utils.comparators.CompareAttributes;
import com.romanidze.perpenanto.utils.comparators.StreamCompareAttributes;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ProfileServiceImpl implements ProfileServiceInterface{

    private ServletContext ctx;

    private ProfileServiceImpl(){}

    public ProfileServiceImpl(ServletContext ctx){

        this.ctx = ctx;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addProfile(Profile model) {

        DBConnection dbConnection = new DBConnection(this.ctx.getResourceAsStream("/WEB-INF/properties/db.properties"));

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConnection.getDBConfig());

        try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                          configMap.get("db_password"))){

            ProfileDAOInterface profileDAO = new ProfileDAOImpl(conn);
            profileDAO.save(model);

        }catch(SQLException e){

            e.printStackTrace();

        }

    }

    @Override
    public void showProfile(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context) {

        HttpSession session = req.getSession(true);
        WorkWithCookie cookieWork = new WorkWithCookie();

        DBConnection dbConnection = new DBConnection(this.ctx.getResourceAsStream("/WEB-INF/properties/db.properties"));

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConnection.getDBConfig());

        try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                          configMap.get("db_password"))){

            UserDAOInterface userDAO = new UserDAOImpl(conn);
            ProfileDAOInterface profileDAO = new ProfileDAOImpl(conn);

            Long userId;

            Optional<Cookie> cookie1 = cookieWork.getCookieByName(req, "remember_me");

            if(cookie1.get().getValue().equals("no")){

                userId = (Long) session.getAttribute("user_id");

            }else{

                Optional<Cookie> cookie2 = cookieWork.getCookieByName(req, "remember_user");

                Long userIdFromSession = (Long) session.getAttribute("user_id");

                if(BCrypt.checkpw(String.valueOf(userIdFromSession), cookie2.get().getValue())){

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
            String country = profile.getAddressToUsers().get(0).getCountry();
            int postIndex = profile.getAddressToUsers().get(0).getPostalCode();
            String city = profile.getAddressToUsers().get(0).getCity();
            String street = profile.getAddressToUsers().get(0).getStreet();
            int homeNumber = profile.getAddressToUsers().get(0).getHomeNumber();

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

        }catch(SQLException e){

            e.printStackTrace();

        }

    }

    @Override
    public List<TempProfile> getProfilesByCookie(HttpServletRequest req, HttpServletResponse resp) {

        DBConnection dbConnection = new DBConnection(this.ctx.getResourceAsStream("/WEB-INF/properties/db.properties"));

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConnection.getDBConfig());

        WorkWithCookie cookieWork = new WorkWithCookie();

        List<TempProfile> tempList = new ArrayList<>();
        List<TempProfile> sortedList = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                          configMap.get("db_password"))){

            ProfileDAOInterface profileDAO = new ProfileDAOImpl(conn);
            Cookie cookie = cookieWork.getCookieWithType(req, resp);

            List<Profile> currentProfiles = profileDAO.findAll();
            ProfileTransferInterface profileDTO = new ProfileTransferImpl();
            tempList.addAll(profileDTO.getTempProfiles(currentProfiles));

            int size = 3;

            Function<TempProfile, String> zero = (TempProfile tp) -> String.valueOf(tp.getId());
            Function<TempProfile, String> first = TempProfile::getPersonName;
            Function<TempProfile, String> second = TempProfile::getPersonSurname;

            List<Function<TempProfile, String>> functions = Arrays.asList(zero, first, second);
            List<String> indexes = Arrays.asList("0", "1", "2");

            Map<String, Function<TempProfile, String>> functionMap = new HashMap<>();

            IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

            CompareAttributes<TempProfile> compareAttr = new StreamCompareAttributes<>();

            sortedList.addAll(compareAttr.sortList(tempList, functionMap, cookie.getValue()));

        }catch(SQLException e){

            e.printStackTrace();

        }

        return sortedList;
    }
}
