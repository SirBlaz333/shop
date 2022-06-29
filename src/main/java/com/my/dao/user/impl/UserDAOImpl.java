package com.my.dao.user.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.user.UserDAO;
import com.my.entity.User;
import com.my.entity.UserBuilder;
import com.mysql.cj.BindValue;
import com.mysql.cj.jdbc.BlobFromLocator;
import com.mysql.cj.protocol.a.BlobValueEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final int BEGIN_INDEX = 1;
    public static final String GET_USER_ID = "SELECT id FROM users WHERE email = ?;";
    public static final String ADD_USER = "INSERT INTO users (firstname, lastname, email, password, newsletter) VALUES (?, ?, ?, ?, ?);";
    public static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    public static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?;";
    public static final String UPDATE_USER = "UPDATE users SET firstname = ?, lastname = ?, email = ?, password = ?, newsletter = ?, image = ? WHERE id = ?;";
    private final DBManager dbManager;

    public UserDAOImpl(DBManager dbManager){
        this.dbManager = dbManager;
    }

    @Override
    public User addUser(User user) throws DBException {
        try(Connection connection = dbManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER);
            int index = BEGIN_INDEX;
            preparedStatement.setString(index++, user.getFirstName());
            preparedStatement.setString(index++, user.getLastname());
            preparedStatement.setString(index++, user.getEmail());
            preparedStatement.setString(index++, user.getPassword());
            preparedStatement.setBoolean(index, user.getNewsletter());
            preparedStatement.execute();
            int userId = getUserId(connection, user);
            user.setId(userId);
            return user;
        } catch (SQLException e){
            throw new DBException("User with such email already exists");
        }
    }

    private int getUserId(Connection connection, User user) throws SQLException, DBException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ID);
        preparedStatement.setString(BEGIN_INDEX, user.getEmail());
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt(BEGIN_INDEX);
        }
        throw new DBException("User has not been inserted");
    }

    @Override
    public void removeUser(User user) throws DBException {
        try(Connection connection = dbManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(BEGIN_INDEX, user.getId());
            preparedStatement.execute();
        } catch (SQLException e){
            throw new DBException("Cannot delete user");
        }
    }

    @Override
    public User getUserByEmail(String email) throws DBException {
        try(Connection connection = dbManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL);
            preparedStatement.setString(BEGIN_INDEX, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getUser(resultSet);
        } catch (SQLException e){
            throw new DBException("User does not exist");
        } catch (IOException e) {
            throw new DBException("Cannot convert image");
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException, IOException {
        int index = BEGIN_INDEX;
        int id = resultSet.getInt(index++);
        String firstname = resultSet.getString(index++);
        String lastname = resultSet.getString(index++);
        String email = resultSet.getString(index++);
        String password = resultSet.getString(index++);
        boolean newsletter = resultSet.getBoolean(index++);
        Blob blob = resultSet.getBlob(index);
        InputStream inputStream = blob.getBinaryStream();
        BufferedImage image = ImageIO.read(inputStream);

        UserBuilder userBuilder = new UserBuilder();
        userBuilder.withId(id)
                .withFirstname(firstname)
                .withLastname(lastname)
                .withEmail(email)
                .withPassword(password)
                .withNewsletter(newsletter)
                .withImage(image);
        return userBuilder.getUser();
    }

    @Override
    public User updateUser(User user) throws DBException {
        try(Connection connection = dbManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            int index = BEGIN_INDEX;
            preparedStatement.setString(index++, user.getFirstName());
            preparedStatement.setString(index++, user.getLastname());
            preparedStatement.setString(index++, user.getEmail());
            preparedStatement.setString(index++, user.getPassword());
            preparedStatement.setBoolean(index++, user.getNewsletter());
            BufferedImage bufferedImage = user.getImage();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            //TODO
            //convert to blob;
            return user;
        } catch (SQLException e){
            throw new DBException("Cannot delete user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DBException {
        return null;
    }
}
