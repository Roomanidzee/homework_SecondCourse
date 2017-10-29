package com.romanidze.perpenanto.services.implementations;

import com.romanidze.perpenanto.dao.implementations.ReservationDAOImpl;
import com.romanidze.perpenanto.dao.interfaces.ReservationDAOInterface;
import com.romanidze.perpenanto.models.Reservation;
import com.romanidze.perpenanto.services.interfaces.ReservationServiceInterface;
import com.romanidze.perpenanto.utils.DBConnection;
import com.romanidze.perpenanto.utils.WorkWithCookie;
import com.romanidze.perpenanto.utils.comparators.CompareAttributes;
import com.romanidze.perpenanto.utils.comparators.StreamCompareAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ReservationServiceImpl implements ReservationServiceInterface{

    private ServletContext ctx;

    private ReservationServiceImpl(){}

    public ReservationServiceImpl(ServletContext ctx){

        this.ctx = ctx;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Reservation> getReservationByCookie(HttpServletRequest req, HttpServletResponse resp) {

        DBConnection dbConnection = new DBConnection(this.ctx.getResourceAsStream("/WEB-INF/properties/db.properties"));

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConnection.getDBConfig());

        WorkWithCookie cookieWork = new WorkWithCookie();

        List<Reservation> sortedReservations = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                          configMap.get("db_password"))){

            ReservationDAOInterface reservationDAO = new ReservationDAOImpl(conn);
            List<Reservation> currentReservations = reservationDAO.findAll();
            Cookie cookie = cookieWork.getCookieWithType(req, resp);

            int size = 3;

            Function<Reservation, String> zero = (Reservation r) -> String.valueOf(r.getId());
            Function<Reservation, String> first = Reservation::getStatus;
            Function<Reservation, String> second = (Reservation r) -> String.valueOf(r.getCreatedAt());

            List<Function<Reservation, String>> functions = Arrays.asList(zero, first, second);
            List<String> indexes = Arrays.asList("0", "1", "2");

            Map<String, Function<Reservation, String>> functionMap = new HashMap<>();

            IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

            CompareAttributes<Reservation> compareAttr = new StreamCompareAttributes<>();

            sortedReservations.addAll(compareAttr.sortList(currentReservations, functionMap, cookie.getValue()));

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return sortedReservations;

    }
}
