package com.romanidze.perpenanto.controllers.user;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.services.interfaces.auth.AuthenticationService;
import com.romanidze.perpenanto.services.interfaces.user.BusketService;
import com.romanidze.perpenanto.services.interfaces.user.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 08.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("Products Catalog Controller Annotation")
public class ProductsCatalogController {

    private final ProductService productService;
    private final BusketService busketService;
    private final AuthenticationService authenticationService;

    @Autowired
    public ProductsCatalogController(ProductService productService, BusketService busketService,
                                     AuthenticationService authenticationService) {
        this.productService = productService;
        this.busketService = busketService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/catalog")
    public ModelAndView getCatalog(){

        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = this.productService.getProducts();

        modelAndView.addObject("products", products);
        modelAndView.setViewName("user/products_catalog");

        return modelAndView;

    }

    @GetMapping("/user/catalog")
    public ModelAndView getCatalogWithBusket(Authentication authentication){

        User user = this.authenticationService.getUserByAuthentication(authentication);
        Integer productsInBusketCount = this.busketService.getProductsCount(user.getProfile());
        List<Product> products = this.productService.getProducts();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("products_count", productsInBusketCount);

        modelAndView.setViewName("user/products_catalog");

        return modelAndView;
    }

    @PostMapping(value = "/user/catalog", params = "productId")
    public ModelAndView addProductToBusket(@RequestParam("productId") Long productId,
                                           Authentication authentication){

        User user = this.authenticationService.getUserByAuthentication(authentication);
        this.busketService.addProductToBusket(user.getProfile(), productId);

        return new ModelAndView("redirect:/user/catalog");

    }

}
