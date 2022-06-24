package com.my.service.user;

import com.my.entity.User;
import com.my.service.ServiceException;

import java.util.List;

public interface UserService {
    User add(User user) throws ServiceException;
    User update(User user);
    boolean exists(String email);
    boolean remove(User user);
    User get(User user) throws ServiceException;
    List<User> getAllUsers();
}
