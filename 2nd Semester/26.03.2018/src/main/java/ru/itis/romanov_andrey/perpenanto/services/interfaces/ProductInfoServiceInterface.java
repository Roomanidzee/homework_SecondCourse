package ru.itis.romanov_andrey.perpenanto.services.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.ProductToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempProductToUser;

import java.util.List;

public interface ProductInfoServiceInterface {

    List<TempProductToUser> getProductInfos();
    List<TempProductToUser> getProductInfosByCookie(String cookieValue);

    void addProductInfo(ProductToUser model);
    void updateProductInfo(ProductToUser model);
    void deleteProductInfo(Long id);

}
