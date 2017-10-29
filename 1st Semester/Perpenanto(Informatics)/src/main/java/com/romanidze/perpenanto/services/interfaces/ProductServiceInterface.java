package com.romanidze.perpenanto.services.interfaces;

import com.romanidze.perpenanto.models.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ProductServiceInterface {

    List<Product> getProductsByCookie(HttpServletRequest req, HttpServletResponse resp);

}
