package com.romanidze.perpenanto.converters;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component("productConverter")
public class StringToProductConverter implements Converter<String, Product> {

    private final ProductRepository productRepository;

    @Autowired
    public StringToProductConverter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product convert(String s) {

        if(s.equals("")){
            return null;
        }

        return this.productRepository.findOne(Long.parseLong(s));

    }
}
