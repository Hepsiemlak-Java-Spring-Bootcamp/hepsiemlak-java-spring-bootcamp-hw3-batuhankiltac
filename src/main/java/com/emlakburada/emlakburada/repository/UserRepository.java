package com.emlakburada.emlakburada.repository;

import com.emlakburada.emlakburada.model.User;
import com.emlakburada.emlakburada.model.enums.UserType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private static final List<User> userList = new ArrayList<>();

    private static User prepareUser(int userId, String name) {
        User user = new User();
        user.setUserType(UserType.INDIVIDUAL);
        user.setUserId(userId);
        user.setName(name);
        return user;
    }

    static {
        userList.add(prepareUser(1, "Batuhan"));
        userList.add(prepareUser(2, "Ahmet"));
        userList.add(prepareUser(3, "Mehmet"));
    }

    public User saveUser(User user) {
        userList.add(user);
        return user;
    }

    public List<User> findAllUsers() {
        return userList;
    }

    public User findUserById(int userId) {
        return userList.stream().filter(user -> user.getUserId() == userId).findAny().orElse(new User());
    }

    public User findUserFavoritesById(int id) {
        return userList.stream()
                .filter(user -> user.getUserId() == id)
                .findFirst().orElse(null);
    }
}