package com.my.service.user;

import com.my.entity.user.User;
import com.my.service.ServiceException;

import java.util.List;

public interface UserService {
    User add(User user, String imagesFilepath) throws ServiceException;
    User update(User user) throws ServiceException;
    void remove(User user) throws ServiceException;
    User login(User user) throws ServiceException;
    List<User> getAllUsers() throws ServiceException;
}
