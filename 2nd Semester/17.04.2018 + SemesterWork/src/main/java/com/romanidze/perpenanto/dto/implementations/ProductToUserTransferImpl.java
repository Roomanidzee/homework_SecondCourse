package com.romanidze.perpenanto.dto.implementations;

import com.romanidze.perpenanto.domain.admin.ProductToUser;
import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.dto.interfaces.ProductToUserTransfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class ProductToUserTransferImpl implements ProductToUserTransfer {

    @Override
    public List<ProductToUser> getProductsToUsers(List<User> users) {

        List<ProductToUser> resultList = new ArrayList<>();

        int listSize = users.size();

        IntStream.range(0, listSize).forEachOrdered(i -> {

            User user = users.get(i);
            Set<Product> products = user.getProducts();

            products.stream()
                    .map(product -> ProductToUser.builder()
                                                 .userId(user.getId())
                                                 .productId(product.getId())
                                                 .build()
                    )
                    .forEachOrdered(resultList::add);

        });

        return resultList;

    }
}