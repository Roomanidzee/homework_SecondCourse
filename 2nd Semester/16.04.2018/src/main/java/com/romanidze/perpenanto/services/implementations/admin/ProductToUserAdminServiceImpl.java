package com.romanidze.perpenanto.services.implementations.admin;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.admin.ProductToUserForm;
import com.romanidze.perpenanto.repositories.ProductRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.services.interfaces.admin.ProductToUserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class ProductToUserAdminServiceImpl implements ProductToUserAdminService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductToUserAdminServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addProductToUser(ProductToUserForm productToUserForm) {

        User user = this.userRepository.findOne(productToUserForm.getUserId());
        Product product = this.productRepository.findOne(productToUserForm.getProductId());

        if(user.getProducts() == null){

            Set<Product> products = new HashSet<>();
            products.add(product);
            user.setProducts(products);

        }else{
            user.getProducts().add(product);
        }

    }

    @Override
    public void updateProductToUser(ProductToUserForm productToUserForm) {

        User user = this.userRepository.findOne(productToUserForm.getUserId());
        Product product = this.productRepository.findOne(productToUserForm.getProductId());

        user.getProducts().add(product);

    }

    @Override
    public void deleteProductToUser(Long userId, Long productId) {

        User user = this.userRepository.findOne(userId);
        Product product = this.productRepository.findOne(productId);

        user.getProducts().stream()
                          .filter(product1 -> product1.equals(product))
                          .forEach(user.getProducts()::remove);

    }
}
