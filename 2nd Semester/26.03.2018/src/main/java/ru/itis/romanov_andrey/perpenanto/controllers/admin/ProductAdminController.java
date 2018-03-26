package ru.itis.romanov_andrey.perpenanto.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.Product;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProductServiceInterface;

import java.util.List;

@Controller("Product Admin Controller Annotation")
public class ProductAdminController {

    @Autowired
    private ProductServiceInterface productService;

    @RequestMapping(value = "/admin/products", method = RequestMethod.GET)
    public ModelAndView getSortedProducts(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        List<Product> products = this.productService.getProductsByCookie(cookieValue);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("admin/products_admin");

        return modelAndView;

    }

    @RequestMapping(value = "/admin/products", params = "form_action", method = RequestMethod.POST)
    public ModelAndView workWithProducts(@RequestParam("form_action") String action,
                                         @RequestParam(value = "id", defaultValue = "0") Long id,
                                         @RequestParam(value = "title", defaultValue = "") String title,
                                         @RequestParam(value = "price", defaultValue = "") Integer price,
                                         @RequestParam(value = "description", defaultValue = "") String description,
                                         @RequestParam(value = "photo_link", defaultValue = "") String photoLink)
    {

        Product product = Product.builder()
                                 .id(id)
                                 .title(title)
                                 .price(price)
                                 .description(description)
                                 .photo_link(photoLink)
                                 .build();

        switch(action){

            case "add":
                this.productService.addProduct(product);
                break;
            case "update":
                this.productService.updateProduct(product);
                break;
            case "delete":
                this.productService.deleteProduct(id);
                break;

        }

        List<Product> products = this.productService.getProducts();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("admin/products_admin");

        return modelAndView;

    }

}
