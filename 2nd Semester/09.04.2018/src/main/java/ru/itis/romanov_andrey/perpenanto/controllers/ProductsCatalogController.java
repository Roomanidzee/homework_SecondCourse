package ru.itis.romanov_andrey.perpenanto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.Product;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProductServiceInterface;

import java.util.List;

/**
 * 08.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("Products Catalog Controller Annotation")
public class ProductsCatalogController {

    private final ProductServiceInterface productService;

    @Autowired
    public ProductsCatalogController(ProductServiceInterface productService) {
        this.productService = productService;
    }

    @GetMapping("/catalog")
    public ModelAndView getCatalog(){

        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = this.productService.getProducts();

        modelAndView.addObject("products", products);
        modelAndView.setViewName("products_catalog");

        return modelAndView;

    }

    @PostMapping("/search")
    public ModelAndView getProductsByQuery(@RequestParam("search_query") String query){

        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = this.productService.getProductsByUserQuery(query);

        modelAndView.addObject("products", products);
        modelAndView.setViewName("products_catalog");

        return modelAndView;

    }

}
