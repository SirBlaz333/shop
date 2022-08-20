package com.my.dao.user.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.user.UserDAO;
import com.my.entity.dto.UserDTO;
import com.my.entity.user.User;
import com.my.entity.user.UserBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final int BEGIN_INDEX = 1;
    public static final String GET_USER_ID = "SELECT id FROM users WHERE email = ?;";
    public static final String ADD_USER = "INSERT INTO users (user_role_id, firstname, lastname, email, password, newsletter) VALUES (?, ?, ?, ?, ?, ?);";

    public static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    public static final String LOGIN_USER = "SELECT * FROM users WHERE email = ? AND password = ?;";
    public static final String UPDATE_USER = "UPDATE users SET user_role_Id = ?, firstname = ?, lastname = ?, email = ?, password = ?, newsletter = ? WHERE id = ?;";
    private final DBManager dbManager;

    public UserDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public User addUser(UserDTO userDTO) throws DBException {
        User user = userDTO.getUser();
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = prepareCreatingUserStatement(connection, userDTO);
            preparedStatement.execute();
            int userId = getUserId(connection, user);
            user.setId(userId);
            return user;
        } catch (SQLException e) {
            throw new DBException("User with such email already exists");
        }
    }

    private PreparedStatement prepareCreatingUserStatement(Connection connection,UserDTO userDTO) throws SQLException {
        User user = userDTO.getUser();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER);
        int index = BEGIN_INDEX;
        preparedStatement.setInt(index++, userDTO.getUserRoleId());
        preparedStatement.setString(index++, user.getFirstname());
        preparedStatement.setString(index++, user.getLastname());
        preparedStatement.setString(index++, user.getEmail());
        preparedStatement.setString(index++, user.getPassword());
        preparedStatement.setBoolean(index, user.getNewsletter());
        return preparedStatement;
    }

    private int getUserId(Connection connection, User user) throws SQLException, DBException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ID);
        preparedStatement.setString(BEGIN_INDEX, user.getEmail());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(BEGIN_INDEX);
        }
        throw new DBException("User has not been inserted");
    }

    @Override
    public void removeUser(User user) throws DBException {
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(BEGIN_INDEX, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DBException("Cannot delete user");
        }
    }

    @Override
    public UserDTO loginUser(User user) throws DBException {
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_USER);
            int index = BEGIN_INDEX;
            preparedStatement.setString(index++, user.getEmail());
            preparedStatement.setString(index, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildUser(resultSet);
        } catch (SQLException e) {
            throw new DBException("You entered wrong email or password");
        } catch (IOException e) {
            throw new DBException("Cannot convert image");
        }
    }

    private UserDTO buildUser(ResultSet resultSet) throws SQLException, IOException {
        int index = BEGIN_INDEX;
        int id = resultSet.getInt(index++);
        int userRoleId = resultSet.getInt(index++);
        String firstname = resultSet.getString(index++);
        String lastname = resultSet.getString(index++);
        String email = resultSet.getString(index++);
        String password = resultSet.getString(index++);
        boolean newsletter = resultSet.getBoolean(index);
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.withId(id)
                .withFirstname(firstname)
                .withLastname(lastname)
                .withEmail(email)
                .withPassword(password)
                .withNewsletter(newsletter);
        User user = userBuilder.getUser();
        return new UserDTO(user, userRoleId);
    }

    @Override
    public User updateUser(User user) throws DBException {
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            int index = BEGIN_INDEX;
            preparedStatement.setString(index++, user.getFirstname());
            preparedStatement.setString(index++, user.getLastname());
            preparedStatement.setString(index++, user.getEmail());
            preparedStatement.setString(index++, user.getPassword());
            preparedStatement.setBoolean(index, user.getNewsletter());
            return user;
        } catch (SQLException e) {
            throw new DBException("Cannot update user");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() throws DBException {
        List<UserDTO> users = new ArrayList<>();
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserDTO userDTO = buildUser(resultSet);
                users.add(userDTO);
            }
            return users;
        } catch (SQLException e) {
            throw new DBException("Cannot update user");
        } catch (IOException e) {
            throw new DBException("Cannot convert image");
        }
    }
}
