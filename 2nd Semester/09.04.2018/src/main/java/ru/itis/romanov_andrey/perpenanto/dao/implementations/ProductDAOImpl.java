package ru.itis.romanov_andrey.perpenanto.dao.implementations;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProductDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 12.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Repository
public class ProductDAOImpl implements ProductDAOInterface{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll() {
        return (List<Product>) this.entityManager.createQuery("SELECT p from Product p join fetch p.profiles," +
                                                                 "join fetch p.users, join fetch p.reservations")
                                                 .getResultList();
    }

    @Override
    @Transactional
    public void save(Product product) {
        this.entityManager.persist(product);
        this.entityManager.flush();
    }

    @Override
    public Product find(Long id) {
        return this.entityManager.find(Product.class, id);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Product product = find(id);
        this.entityManager.remove(product);
        this.entityManager.flush();

    }

    @Override
    @Transactional
    public void update(Product model) {

        Product product = find(model.getId());

        this.entityManager.detach(product);
        product = Product.builder()
                         .id(model.getId())
                         .title(model.getTitle())
                         .price(model.getPrice())
                         .description(model.getDescription())
                         .photoLink(model.getPhotoLink())
                         .build();
        this.entityManager.merge(product);

    }

    @Override
    public Product findByTitle(String title) {
        return (Product) this.entityManager.createQuery("SELECT p FROM Product p where p.title = :title")
                                           .setParameter("title", title)
                                           .getSingleResult();
    }

    @Override
    public List<Product> findAllByTitle(String title) {

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        ParameterExpression<String> queryParam = cb.parameter(String.class);

        query.select(root).where(cb.equal(root.get("title"), queryParam));
        TypedQuery<Product> finalQuery = this.entityManager.createQuery(query);
        finalQuery.setParameter(queryParam, title);

        return finalQuery.getResultList();

    }
}
