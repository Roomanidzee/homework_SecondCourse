package ru.itis.romanov_andrey.perpenanto.controllers.admin;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.romanov_andrey.perpenanto.models.ProductToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempProductToUser;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProductInfoServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProductServiceInterface;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.UsersServiceInterface;

import java.util.List;

@Controller("ProductInfo Admin Controller")
public class ProductInfoAdminController {

    @Autowired
    private ProductInfoServiceInterface productInfoService;

    @Autowired
    private UsersServiceInterface userService;

    @Autowired
    private ProductServiceInterface productService;

    @RequestMapping(value = "/admin/product_infos", method = RequestMethod.GET)
    public ModelAndView getSortedProductInfos(@CookieValue(value = "status", defaultValue = "-1") String cookieValue){

        List<TempProductToUser> productInfos = this.productInfoService.getProductInfosByCookie(cookieValue);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product_infos", productInfos);
        modelAndView.setViewName("admin/product_info_admin");

        return modelAndView;

    }

    @RequestMapping(value = "/admin/product_infos", params = "form_action", method = RequestMethod.POST)
    public ModelAndView workWithProductInfos(@RequestParam("form_action") String action,
                                             @RequestParam(value = "id", defaultValue = "0") Long id,
                                             @RequestParam(value = "user_id", defaultValue = "0") Long userId,
                                             @RequestParam(value = "product_id", defaultValue = "0") Long productId)
    {

        ProductToUser productToUser = ProductToUser.builder()
                                             .id(id)
                                             .user(this.userService.getUserByID(userId))
                                             .products(Lists.newArrayList())
                                             .build();

        productToUser.getProducts().add(this.productService.getProductById(productId));

        switch(action){

            case "add":

                this.productInfoService.addProductInfo(productToUser);
                break;

            case "update":

                this.productInfoService.updateProductInfo(productToUser);
                break;

            case "delete":

                this.productInfoService.deleteProductInfo(id);
                break;

        }

        List<TempProductToUser> productInfos = this.productInfoService.getProductInfos();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product_infos", productInfos);
        modelAndView.setViewName("admin/product_info_admin");

        return modelAndView;

    }

}
