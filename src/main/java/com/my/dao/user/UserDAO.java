package com.my.dao.user;

import com.my.entity.User;

import java.util.List;

public interface UserDAO {
    User addUser(User user);
    boolean removeUser(User user);
    User getUserByEmail(String email);
    User updateUser(User user);
    List<User> getAllUsers();
}
