package ru.itis.java_enterprise_course.romanov_andrey.dao.interfaces;

import java.util.List;

public interface CrudDAOInterface<M, I> {

    List<M> findAll();
    void save(M model);
    M find(I id);
    void delete(I id);
    void update(M model);

}
