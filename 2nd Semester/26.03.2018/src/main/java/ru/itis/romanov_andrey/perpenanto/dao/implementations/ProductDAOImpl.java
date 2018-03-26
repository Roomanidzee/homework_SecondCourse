package ru.itis.romanov_andrey.perpenanto.dao.implementations;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProductDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Product;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDAOImpl implements ProductDAOInterface {

    private JdbcTemplate template;
    private Map<Long, Product> products;

    private static final String FIND_ALL_QUERY = "SELECT * FROM product";
    private static final String INSERT_QUERY = "INSERT INTO product(title, price,description, photo_link) " +
            "VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM product WHERE product.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM product WHERE product.id = ?";
    private static final String UPDATE_QUERY = "UPDATE product SET(title, price, description, photo_link) = (?, ?, ?, ?)" +
            "WHERE product.id = ?";
    private static final String FIND_ALL_BY_TITLE_QUERY = "SELECT * FROM product WHERE product.title = ?";
    private static final String FIND_ALL_BY_PRICE_QUERY = "SELECT * FROM product WHERE product.price = ?";
    private static final String UPDATE_PHOTO_QUERY = "UPDATE product SET(photo_link) = (?) WHERE product.id = ?";

    private ProductDAOImpl(){}

    @Autowired
    public ProductDAOImpl(DataSource dataSource){

        this.template = new JdbcTemplate(dataSource);
        this.products = new HashMap<>();

    }

    private RowMapper<Product> productRowMapper = (resultSet, rowNumber) ->{

        Long currentProductId = resultSet.getLong(1);

        if(this.products.get(currentProductId) == null){

            this.products.put(currentProductId, Product.builder()
                                                       .id(currentProductId)
                                                       .title(resultSet.getString(2))
                                                       .price(resultSet.getInt(3))
                                                       .description(resultSet.getString(4))
                                                       .photo_link(resultSet.getString(5))
                                                       .build());

        }

        return this.products.get(currentProductId);

    };

    @Override
    public List<Product> findAll() {

        this.template.query(FIND_ALL_QUERY, this.productRowMapper);

        List<Product> result = Lists.newArrayList(this.products.values());

        this.products.clear();

        return result;

    }

    @Override
    public void save(Product model) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(
                connection -> {

                    PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                    ps.setString(1, model.getTitle());
                    ps.setInt(2, model.getPrice());
                    ps.setString(3, model.getDescription());
                    ps.setString(4, model.getPhoto_link());
                    return ps;

                }, keyHolder);

        model.setId(keyHolder.getKey().longValue());

    }

    @Override
    public Product find(Long id) {

        Product result = this.template.query(FIND_BY_ID_QUERY, new Long[]{id}, this.productRowMapper).get(0);

        this.products.clear();

        return result;

    }

    @Override
    public void delete(Long id) {
        this.template.update(DELETE_QUERY, id);
        this.products.clear();
    }

    @Override
    public void update(Product model) {

        this.template.update(UPDATE_QUERY, new String[]{model.getTitle()}, new Integer[]{model.getPrice()},
                new String[]{model.getDescription()},new String[]{model.getPhoto_link()},
                new Long[]{model.getId()});

    }

    @Override
    public List<Product> findAllByTitle(String title) {

        this.template.query(FIND_ALL_BY_TITLE_QUERY, new String[]{title}, this.productRowMapper);

        List<Product> result = Lists.newArrayList(this.products.values());

        this.products.clear();

        return result;

    }

    @Override
    public List<Product> findAllByPrice(Integer price) {

        this.template.query(FIND_ALL_BY_PRICE_QUERY, new Integer[]{price}, this.productRowMapper);

        List<Product> result = Lists.newArrayList(this.products.values());

        this.products.clear();

        return result;
    }

    @Override
    public void updatePhoto(Product product) {

        this.template.update(UPDATE_PHOTO_QUERY, new String[]{product.getPhoto_link()},
                new Long[]{product.getId()});

    }
}

