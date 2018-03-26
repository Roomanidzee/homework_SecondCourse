package ru.itis.romanov_andrey.perpenanto.dao.implementations;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.UserDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAOInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = builder.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root);

        return this.entityManager.createQuery(cq).getResultList();

    }

    @Override
    @Transactional
    public void save(User model) {

        try{

            this.entityManager.getTransaction().begin();
            this.entityManager.persist(model);
            this.entityManager.getTransaction().commit();

        }catch(Exception e){

            this.entityManager.getTransaction().rollback();
            e.printStackTrace();

        }finally{
            this.entityManager.flush();
        }

    }

    @Override
    public User find(Long id) {

        User user = this.entityManager.find(User.class, id);
        this.entityManager.flush();

        return user;

    }

    @Override
    @Transactional
    public void delete(Long id) {

        try{

            User tempUser = find(id);

            this.entityManager.getTransaction().begin();
            this.entityManager.remove(tempUser);
            this.entityManager.getTransaction().commit();

        }catch(Exception e){

            this.entityManager.getTransaction().rollback();
            e.printStackTrace();

        }finally{
            this.entityManager.flush();
        }

    }

    @Override
    @Transactional
    public void update(User model) {

        try{

            User tempUser = find(model.getId());

            this.entityManager.detach(tempUser);

            this.entityManager.getTransaction().begin();
            tempUser = User.builder()
                           .id(model.getId())
                           .username(model.getUsername())
                           .password(model.getPassword())
                           .build();

            this.entityManager.merge(tempUser);
            this.entityManager.getTransaction().commit();

        }catch(Exception e){

            this.entityManager.getTransaction().rollback();
            e.printStackTrace();

        }finally{
            this.entityManager.flush();
        }

    }

    @Override
    public User findByUsername(String username) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = builder.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root)
          .where(builder.equal(root.get("username_or_email"), username));

        return this.entityManager.createQuery(cq).getSingleResult();

    }
}
