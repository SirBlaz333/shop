package com.my.service.user;

import com.my.cmd.impl.ShowLoginPageCommand;
import com.my.dao.DBException;
import com.my.dao.user.UserDAO;
import com.my.entity.User;
import com.my.service.ServiceException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserServiceImpl implements UserService{

    public static final String USER_ALREADY_EXISTS = "User with such email already exists";
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public User add(User user, String imagesFilepath) throws ServiceException {
        try{
            user = userDAO.addUser(user);
            writeImage(user, imagesFilepath);
            return user;
        } catch (DBException | IOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    private void writeImage(User user, String imagesFilepath) throws IOException {
        BufferedImage bufferedImage = user.getImage();
        if(bufferedImage != null){
            File file = new File(imagesFilepath + user.getId());
            ImageIO.write(bufferedImage, ShowLoginPageCommand.IMAGE_FORMAT, file);
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
