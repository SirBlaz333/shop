package com.my.user.service;

import com.my.user.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User update(User user);
    boolean remove(User user);
    User get(User user);
    List<User> getAllUsers();
}
