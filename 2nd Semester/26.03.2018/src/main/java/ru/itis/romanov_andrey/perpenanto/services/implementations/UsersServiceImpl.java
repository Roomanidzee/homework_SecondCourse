package ru.itis.romanov_andrey.perpenanto.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.UserDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.User;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.UsersServiceInterface;
import ru.itis.romanov_andrey.perpenanto.utils.CompareAttributes;
import ru.itis.romanov_andrey.perpenanto.utils.StreamCompareAttributes;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

@Service
public class UsersServiceImpl implements UsersServiceInterface{

    @Autowired
    private UserDAOInterface userDAO;

    @Override
    public User getUserByID(Long id) {
        return this.userDAO.find(id);
    }

    @Override
    public List<User> getUsers() {
        return this.userDAO.findAll();
    }

    @Override
    public List<User> getUserByCookie(String cookieValue) {

        List<User> currentUsers = this.userDAO.findAll();
        List<User> sortedUsers = new ArrayList<>();

        int size = 3;

        Function<User, String> zero = (User u) -> String.valueOf(u.getId());
        Function<User, String> first = User::getUsername;
        Function<User, String> second = User::getPassword;

        List<Function<User, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<User, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<User> compareAttr = new StreamCompareAttributes<>();
        sortedUsers.addAll(compareAttr.sortList(currentUsers, functionMap, cookieValue));

        return sortedUsers;

    }

    @Override
    public void addUser(User model) {
        this.userDAO.save(model);
    }

    @Override
    public void updateUser(User model) {
        this.userDAO.update(model);
    }

    @Override
    public void deleteUser(Long id) {
        this.userDAO.delete(id);
    }
}
