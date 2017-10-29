package com.romanidze.perpenanto.services.implementations;

import com.romanidze.perpenanto.dao.implementations.BusketDAOImpl;
import com.romanidze.perpenanto.dao.interfaces.BusketDAOInterface;
import com.romanidze.perpenanto.models.Busket;
import com.romanidze.perpenanto.models.Product;
import com.romanidze.perpenanto.services.interfaces.BusketServiceInterface;
import com.romanidze.perpenanto.utils.DBConnection;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BusketServiceImpl implements BusketServiceInterface{

    private ServletContext ctx;

    private BusketServiceImpl(){}

    public BusketServiceImpl(ServletContext ctx){

        this.ctx = ctx;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Integer getPriceForProducts(Long busketId) {

        DBConnection dbConnection = new DBConnection(this.ctx.getResourceAsStream("/WEB-INF/properties/db.properties"));

        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.putAll(dbConnection.getDBConfig());

        int result = 0;

        try(Connection conn = DriverManager.getConnection(configMap.get("db_url"), configMap.get("db_username"),
                                                          configMap.get("db_password"))){

            BusketDAOInterface busketDAO = new BusketDAOImpl(conn);

            Busket busket = busketDAO.find(busketId);
            List<Product> products = busket.getProducts();

            result = products.stream().mapToInt(Product::getPrice).sum();

        }catch(SQLException e){

            e.printStackTrace();

        }

        return result;

    }
}
