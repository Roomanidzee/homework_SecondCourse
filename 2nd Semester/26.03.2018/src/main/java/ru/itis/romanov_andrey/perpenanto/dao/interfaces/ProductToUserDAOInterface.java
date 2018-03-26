package ru.itis.romanov_andrey.perpenanto.dao.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.ProductToUser;

public interface ProductToUserDAOInterface extends CrudDAOInterface<ProductToUser, Long>{
    void deleteAllByUser(Long userId);
}
