package ru.itis.romanov_andrey.perpenanto.dao.implementations;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.AddressToUserDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.AddressToUser;
import ru.itis.romanov_andrey.perpenanto.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class AddressToUserDAOImpl implements AddressToUserDAOInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AddressToUser> findAll() {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<AddressToUser> cq = builder.createQuery(AddressToUser.class);
        Root<AddressToUser> root = cq.from(AddressToUser.class);

        root.join("user_id", JoinType.LEFT);

        cq.select(root);

        return this.entityManager.createQuery(cq).getResultList();

    }

    @Override
    @Transactional
    public void save(AddressToUser model) {

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
    public AddressToUser find(Long id) {

        AddressToUser address = this.entityManager.find(AddressToUser.class, id);

        this.entityManager.flush();
        return address;

    }

    @Override
    @Transactional
    public void delete(Long id) {

        try{

            AddressToUser tempAddress = find(id);

            this.entityManager.getTransaction().begin();
            this.entityManager.remove(tempAddress);
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
    public void update(AddressToUser model) {

        try{

            AddressToUser address = find(model.getId());

            this.entityManager.detach(address);

            this.entityManager.getTransaction().begin();
            address = AddressToUser.builder()
                                   .id(model.getId())
                                   .userId(model.getUser().getId())
                                   .country(model.getCountry())
                                   .postIndex(model.getPostIndex())
                                   .city(model.getCity())
                                   .street(model.getStreet())
                                   .homeNumber(model.getHomeNumber())
                                   .build();

            this.entityManager.merge(address);
            this.entityManager.getTransaction().commit();

        }catch(Exception e){

            this.entityManager.getTransaction().rollback();
            e.printStackTrace();

        }finally{
            this.entityManager.flush();
        }

    }

    @Override
    public List<AddressToUser> findByUser(User user) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<AddressToUser> cq = builder.createQuery(AddressToUser.class);
        Root<AddressToUser> root = cq.from(AddressToUser.class);

        cq.select(root)
          .where(builder.equal(root.get("user_id"), user.getId()));

        return this.entityManager.createQuery(cq).getResultList();

    }
}
