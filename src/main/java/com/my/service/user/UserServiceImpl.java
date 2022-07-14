package com.my.service.user;

import com.my.dao.user.UserDAO;
import com.my.entity.User;
import com.my.service.ServiceException;

import java.util.List;

public class UserServiceImpl implements UserService{

    public static final String USER_ALREADY_EXISTS = "User with such email already exists";
    public static final String USER_DOES_NOT_EXIST = "User does not exist";
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public User add(User user) throws ServiceException {
        User existedUser = get(user);
        if(existedUser != null){
            throw new ServiceException(USER_ALREADY_EXISTS);
        }
        return userDAO.addUser(user);
    }

    @Override
    public User update(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public boolean exists(String email) {
        User user = userDAO.getUserByEmail(email);
        return user != null;
    }

    @Override
    public boolean remove(User user) {
        return userDAO.removeUser(user);
    }

    @Override
    public User get(User user) throws ServiceException {
        String email = user.getEmail();
        if(email == null){
            throw new ServiceException(USER_DOES_NOT_EXIST);
        }
        return userDAO.getUserByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}