package com.my.dao.user;

import com.my.dao.DBException;
import com.my.entity.User;

import java.util.List;

public interface UserDAO {
    User addUser(User user) throws DBException;
    void removeUser(User user) throws DBException;
    User getUserByEmail(String email) throws DBException;
    User updateUser(User user) throws DBException;
    List<User> getAllUsers() throws DBException;
}
