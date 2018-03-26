package ru.itis.romanov_andrey.perpenanto.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProductDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Product;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProductServiceInterface;
import ru.itis.romanov_andrey.perpenanto.utils.CompareAttributes;
import ru.itis.romanov_andrey.perpenanto.utils.StreamCompareAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

@Service
public class ProductServiceImpl implements ProductServiceInterface{

    @Autowired
    private ProductDAOInterface productDAO;

    @Override
    public List<Product> getProducts() {
        return this.productDAO.findAll();
    }

    @Override
    public List<Product> getProductsByCookie(String cookieValue) {

        List<Product> currentProducts = this.productDAO.findAll();
        List<Product> sortedProducts = new ArrayList<>();

        int size = 3;

        Function<Product, String> zero = (Product p) -> String.valueOf(p.getId());
        Function<Product, String> first = Product::getTitle;
        Function<Product, String> second = (Product p) -> String.valueOf(p.getPrice());

        List<Function<Product, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<Product, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<Product> compareAttr = new StreamCompareAttributes<>();

        sortedProducts.addAll(compareAttr.sortList(currentProducts, functionMap, cookieValue));

        return sortedProducts;

    }

    @Override
    public void addProduct(Product model) {
        this.productDAO.save(model);
    }

    @Override
    public void updateProduct(Product model) {
        this.productDAO.update(model);
    }

    @Override
    public void deleteProduct(Long id) {
        this.productDAO.delete(id);
    }

    @Override
    public Product getProductById(Long id) {
        return this.productDAO.find(id);
    }
}
