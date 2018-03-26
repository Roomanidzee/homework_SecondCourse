package ru.itis.romanov_andrey.perpenanto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.Product;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProductServiceInterface;

import java.util.List;

@Controller("Products Catalog Controller Annotation")
public class ProductsCatalogController {

    @Autowired
    private ProductServiceInterface productService;

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public ModelAndView getCatalog(){

        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = this.productService.getProducts();

        modelAndView.addObject("products", products);
        modelAndView.setViewName("products_catalog");

        return modelAndView;

    }

}
