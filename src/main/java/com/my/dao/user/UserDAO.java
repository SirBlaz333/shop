package com.my.dao.user;

import com.my.dao.DBException;
import com.my.entity.dto.UserDTO;
import com.my.entity.user.User;

import java.util.List;

public interface UserDAO {
    User addUser(UserDTO user) throws DBException;
    void removeUser(User user) throws DBException;
    UserDTO loginUser(User user) throws DBException;
    User updateUser(User user) throws DBException;
    List<UserDTO> getAllUsers() throws DBException;
}
