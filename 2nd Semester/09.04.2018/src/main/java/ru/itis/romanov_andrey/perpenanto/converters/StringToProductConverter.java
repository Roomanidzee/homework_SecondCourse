package ru.itis.romanov_andrey.perpenanto.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProductDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Product;

/**
 * 06.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component("productConverter")
public class StringToProductConverter implements Converter<String, Product>{

    private final ProductDAOInterface productDAOInterface;

    @Autowired
    public StringToProductConverter(ProductDAOInterface productDAOInterface) {
        this.productDAOInterface = productDAOInterface;
    }

    @Override
    public Product convert(String s) {

        if(s.equals("")){
            return null;
        }

        return this.productDAOInterface.find(Long.parseLong(s));

    }
}
