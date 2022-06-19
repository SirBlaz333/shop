package com.my.user.dao;

import com.my.user.User;

import java.util.List;

public interface UserDAO {
    User addUser(User user);
    boolean removeUser(User user);
    User getUserByEmail(String email);
    User updateUser(User user);
    List<User> getAllUsers();
}
