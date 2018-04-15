package com.romanidze.perpenanto.services.implementations.user;

import com.romanidze.perpenanto.domain.admin.ProductToUser;
import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.dto.interfaces.ProductToUserTransfer;
import com.romanidze.perpenanto.repositories.ProductRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.services.interfaces.user.ProductService;
import com.romanidze.perpenanto.utils.CompareAttributes;
import com.romanidze.perpenanto.utils.StreamCompareAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductToUserTransfer productToUserDTO;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository,
                              ProductToUserTransfer productToUserDTO) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productToUserDTO = productToUserDTO;
    }

    @Override
    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCookie(String cookieValue) {

        List<Product> currentProducts = this.productRepository.findAll();

        int size = 3;

        Function<Product, String> zero = (Product p) -> String.valueOf(p.getId());
        Function<Product, String> first = Product::getTitle;
        Function<Product, String> second = (Product p) -> String.valueOf(p.getPrice());

        List<Function<Product, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<Product, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<Product> compareAttr = new StreamCompareAttributes<>();

        return new ArrayList<>(compareAttr.sortList(currentProducts, functionMap, cookieValue));

    }

    @Override
    public List<ProductToUser> getProductsToUser() {
        return this.productToUserDTO.getProductsToUsers(this.userRepository.findAll());
    }

    @Override
    public List<ProductToUser> getProductsToUserByCookie(String cookieValue) {

        List<ProductToUser> currentProductToUsers = this.productToUserDTO.getProductsToUsers(this.userRepository.findAll());

        int size = 2;

        Function<ProductToUser, String> first = (ProductToUser tpi) -> String.valueOf(tpi.getUserId());
        Function<ProductToUser, String> second = (ProductToUser tpi) -> String.valueOf(tpi.getProductId());

        List<Function<ProductToUser, String>> functions = Arrays.asList( first, second);
        List<String> indexes = Arrays.asList( "1", "2");

        Map<String, Function<ProductToUser, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<ProductToUser> compareAttr = new StreamCompareAttributes<>();

        return new ArrayList<>(compareAttr.sortList(currentProductToUsers, functionMap, cookieValue));

    }

    @Override
    public void saveOrUpdate(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        this.productRepository.delete(id);
    }
}
