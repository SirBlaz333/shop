package com.my.user.service;

import com.my.exception.ServiceException;
import com.my.user.User;

import java.util.List;

public interface UserService {
    User create(User user) throws ServiceException;
    User update(User user);
    boolean exists(String email);
    boolean remove(User user);
    User get(User user) throws ServiceException;
    List<User> getAllUsers();
}
