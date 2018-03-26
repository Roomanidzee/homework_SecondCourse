package ru.itis.romanov_andrey.perpenanto.dao.implementations;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProductDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProductToUserDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Product;
import ru.itis.romanov_andrey.perpenanto.models.ProductToUser;
import ru.itis.romanov_andrey.perpenanto.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Repository
public class ProductToUserDAOImpl implements ProductToUserDAOInterface {

    private JdbcTemplate template;
    private Map<Long, ProductToUser> productInfos;
    private DataSource dataSource;

    private static final String FIND_ALL_QUERY = "SELECT * FROM product_info " +
            "LEFT JOIN users ON users.id = product_info.user_id " +
            "LEFT JOIN product ON product.id = product_info.product_id";
    private static final String INSERT_QUERY = "INSERT INTO product_info(user_id, product_id) VALUES (?, ?)";
    private static final String FIND_QUERY = "SELECT * FROM product_info WHERE product_info.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM product_info WHERE product_info.id = ?";
    private static final String UPDATE_QUERY = "UPDATE product_info SET(user_id, product_id) = (?, ?) " +
                                                 "WHERE product_info.id = ?";
    private static final String DELETE_ALL_BY_USER_QUERY = "DELETE FROM product_info WHERE product_info.user_id = ?";

    private ProductToUserDAOImpl(){}

    @Autowired
    public ProductToUserDAOImpl(DataSource dataSource){

        this.template = new JdbcTemplate(dataSource);
        this.productInfos = new HashMap<>();
        this.dataSource = dataSource;

    }

    private RowMapper<ProductToUser> productInfoRowMapper = (resultSet, rowNumber) -> {

        Long currentProductInfoId = resultSet.getLong(1);

        if(this.productInfos.get(currentProductInfoId) == null){

            this.productInfos.put(currentProductInfoId, ProductToUser.builder()
                                                                   .id(currentProductInfoId)
                                                                   .user(new User())
                                                                   .products(Lists.newArrayList())
                                                                   .build());

        }

        if(resultSet.getLong(4) != 0){

            User user = User.builder()
                            .id(resultSet.getLong(4))
                            .username(resultSet.getString(5))
                            .password(resultSet.getString(6))
                            .build();

            this.productInfos.get(currentProductInfoId).setUser(user);

        }

        if(resultSet.getLong(7) != 0){

            Product product = Product.builder()
                                     .id(resultSet.getLong(7))
                                     .title(resultSet.getString(8))
                                     .price(resultSet.getInt(9))
                                     .description(resultSet.getString(10))
                                     .photo_link(resultSet.getString(11))
                                     .build();

            this.productInfos.get(currentProductInfoId).getProducts().add(product);

        }

        return this.productInfos.get(currentProductInfoId);

    };

    @Override
    public List<ProductToUser> findAll() {

        this.template.query(FIND_ALL_QUERY, this.productInfoRowMapper);

        List<ProductToUser> result = Lists.newArrayList(this.productInfos.values());

        this.productInfos.clear();

        return result;

    }

    @Override
    public void save(ProductToUser model) {

        int productsCount = model.getProducts().size();

        if(productsCount > 1){

            IntStream.range(0, productsCount).forEachOrdered(i -> {

                KeyHolder keyHolder = new GeneratedKeyHolder();

                this.template.update(
                        connection -> {

                            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                            ps.setLong(1, model.getUser().getId());
                            ps.setLong(2, model.getProducts().get(i).getId());
                            return ps;

                        }, keyHolder);

                model.setId(keyHolder.getKey().longValue());

            });

        }

        if(productsCount == 1){

            KeyHolder keyHolder = new GeneratedKeyHolder();

            this.template.update(
                    connection -> {

                        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                        ps.setLong(1, model.getUser().getId());
                        ps.setLong(2, model.getProducts().get(0).getId());
                        return ps;

                    }, keyHolder);

            model.setId(keyHolder.getKey().longValue());

        }else{

            throw new NullPointerException("У пользователя нет товаров!");

        }

    }

    @Override
    public ProductToUser find(Long id) {

        ProductToUser result = this.template.query(FIND_QUERY, new Long[]{id}, this.productInfoRowMapper).get(0);

        this.productInfos.clear();

        return result;

    }

    @Override
    public void delete(Long id) {

        ProductToUser productToUser = find(id);

        Long productId = productToUser.getProducts().get(0).getId();

        ProductDAOInterface productDAO = new ProductDAOImpl(this.dataSource);
        productDAO.delete(productId);

        this.template.update(DELETE_QUERY, id);

    }

    @Override
    public void update(ProductToUser model) {

        int productCount = model.getProducts().size();

        if(productCount > 1){

            Long currentUserId = model.getUser().getId();

            deleteAllByUser(currentUserId);

            save(model);

            List<Product> products = new ArrayList<>(productCount);
            products.addAll(model.getProducts());

            ProductDAOInterface productDAO = new ProductDAOImpl(this.dataSource);
            products.forEach(productDAO::save);

        }
        if(productCount == 1){

            this.template.update(UPDATE_QUERY, new Long[]{model.getUser().getId()},
                                               new Long[]{model.getProducts().get(0).getId()},
                                               new Long[]{model.getId()});

        }else{

            throw new NullPointerException("У пользователя нет товаров!");

        }

    }

    @Override
    public void deleteAllByUser(Long userId) {
        this.template.update(DELETE_ALL_BY_USER_QUERY, userId);

        ProductDAOInterface productDAO = new ProductDAOImpl(this.dataSource);
        productDAO.delete(productDAO.find(userId).getId());
    }
}
