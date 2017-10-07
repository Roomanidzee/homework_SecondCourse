package com.romanidze.jizzyshop.dao.interfaces;

import com.romanidze.jizzyshop.models.User;

public interface UserDAOInterface extends CrudDAOInterface<User, Long>{

    User findByUsername(String username);

}
