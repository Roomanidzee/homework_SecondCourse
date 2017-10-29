package com.romanidze.perpenanto.services.implementations;

import com.romanidze.perpenanto.dao.implementations.ProductDAOImpl;
import com.romanidze.perpenanto.dao.interfaces.ProductDAOInterface;
import com.romanidze.perpenanto.models.Product;
import com.romanidze.perpenanto.services.interfaces.ProductServiceInterface;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ProductServiceImpl implements ProductServiceInterface{

    private ServletContext ctx;

    private ProductServiceImpl(){}

    public ProductServiceImpl(ServletContext ctx){

        this.ctx = ctx;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> getProductsByCookie(HttpServletRequest req, HttpServletResponse resp) {

        DBConnection dbConnection = new DBConnection(this.ctx.getResourceAsStream("/WEB-INF/properties/db.properties"));

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConnection.getDBConfig());

        WorkWithCookie cookieWork = new WorkWithCookie();

        List<Product> sortedProducts = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                          configMap.get("db_password"))){

            ProductDAOInterface productDAO = new ProductDAOImpl(conn);
            List<Product> currentProducts = productDAO.findAll();

            int size = 3;

            Function<Product, String> zero = (Product p) -> String.valueOf(p.getId());
            Function<Product, String> first = Product::getTitle;
            Function<Product, String> second = (Product p) -> String.valueOf(p.getPrice());

            List<Function<Product, String>> functions = Arrays.asList(zero, first, second);
            List<String> indexes = Arrays.asList("0", "1", "2");
            Cookie cookie = cookieWork.getCookieWithType(req, resp);

            Map<String, Function<Product, String>> functionMap = new HashMap<>();

            IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

            CompareAttributes<Product> compareAttr = new StreamCompareAttributes<>();

            sortedProducts.addAll(compareAttr.sortList(currentProducts, functionMap, cookie.getValue()));

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return sortedProducts;
    }
}
