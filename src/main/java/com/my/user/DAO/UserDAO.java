package com.my.user.DAO;

import com.my.user.User;

import java.util.List;

public interface UserDAO {
    User addUser(User user);
    User removeUser(User user);
    User getUser(User user);
    User updateUser(User user);
    List<User> getAllUsers();
}
