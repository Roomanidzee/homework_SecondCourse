package com.romanidze.perpenanto.services.implementations.admin;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Reservation;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.admin.ProductForm;
import com.romanidze.perpenanto.repositories.ProductRepository;
import com.romanidze.perpenanto.repositories.ReservationRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.services.interfaces.admin.ProductAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class ProductAdminServiceImpl implements ProductAdminService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ProductAdminServiceImpl(ProductRepository productRepository, UserRepository userRepository,
                                   ReservationRepository reservationRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void addProduct(ProductForm productForm) {

        User user = this.userRepository.findOne(productForm.getUserId());
        Reservation reservation = this.reservationRepository.findOne(productForm.getOrderId());

        Product product = Product.builder()
                                 .title(productForm.getTitle())
                                 .price(productForm.getPrice())
                                 .description(productForm.getDescription())
                                 .photoLink(productForm.getPhotolink())
                                 .users(Collections.singletonList(user))
                                 .reservations(Collections.singletonList(reservation))
                                 .build();

        this.productRepository.save(product);

    }

    @Override
    public void updateProduct(ProductForm productForm) {

        User user = this.userRepository.findOne(productForm.getUserId());
        Reservation reservation = this.reservationRepository.findOne(productForm.getOrderId());
        Product product = this.productRepository.findOne(productForm.getId());

        if(product == null){
            throw new NullPointerException("Product not found!");
        }

        product.setTitle(productForm.getTitle());
        product.setPrice(productForm.getPrice());
        product.setDescription(productForm.getDescription());
        product.setPhotoLink(productForm.getPhotolink());
        product.setUsers(Collections.singletonList(user));
        product.setReservations(Collections.singletonList(reservation));

        this.productRepository.save(product);

    }

    @Override
    public void deleteProduct(Long id) {
        this.productRepository.delete(id);
    }
}
