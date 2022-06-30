package com.my.service.user;

import com.my.dao.DBException;
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
        try{
            return userDAO.addUser(user);
        } catch (DBException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User update(User user) throws ServiceException {
        try{
            return userDAO.updateUser(user);
        } catch (DBException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void remove(User user) throws ServiceException {
        try{
            userDAO.removeUser(user);
        } catch (DBException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User login(User user) throws ServiceException {
        try{
            return userDAO.loginUser(user);
        } catch (DBException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return userDAO.getAllUsers();
        } catch (DBException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
