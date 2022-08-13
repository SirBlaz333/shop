package com.my.service.user;

import com.my.cmd.impl.ShowLoginPageCommand;
import com.my.dao.DBException;
import com.my.dao.user.UserDAO;
import com.my.dao.user.UserRolesDAO;
import com.my.entity.dto.UserDTO;
import com.my.entity.user.User;
import com.my.entity.user.UserRole;
import com.my.service.ServiceException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    public static final String USER_ALREADY_EXISTS = "User with such email already exists";
    private final UserDAO userDAO;
    private final UserRolesDAO userRolesDAO;
    private final UserRoleParser userRoleParser;

    public UserServiceImpl(UserDAO userDAO, UserRolesDAO userRolesDAO) {
        this.userDAO = userDAO;
        this.userRolesDAO = userRolesDAO;
        userRoleParser = new UserRoleParser();
    }

    @Override
    public User add(User user, String imagesFilepath) throws ServiceException {
        try {
            UserRole userRole = UserRole.USER;
            user.setUserRole(userRole);
            int userRoleId = userRolesDAO.getUserRoleId(userRole.toString().toLowerCase());
            UserDTO userDTO = new UserDTO(user, userRoleId);
            user = userDAO.addUser(userDTO);
            writeImage(user, imagesFilepath);
            return user;
        } catch (DBException | IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void writeImage(User user, String imagesFilepath) throws IOException {
        BufferedImage bufferedImage = user.getImage();
        if (bufferedImage != null) {
            File file = new File(imagesFilepath + user.getId());
            ImageIO.write(bufferedImage, ShowLoginPageCommand.IMAGE_FORMAT, file);
        }
    }

    @Override
    public User update(User user) throws ServiceException {
        try {
            return userDAO.updateUser(user);
        } catch (DBException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void remove(User user) throws ServiceException {
        try {
            userDAO.removeUser(user);
        } catch (DBException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User login(User user) throws ServiceException {
        try {
            UserDTO userDTO = userDAO.loginUser(user);
            return buildUser(userDTO);
        } catch (DBException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return userDAO.getAllUsers()
                    .stream()
                    .map(this::buildUser)
                    .collect(Collectors.toList());
        } catch (DBException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private User buildUser(UserDTO userDTO) {
        String userRole = userRolesDAO.getUserRoleById(userDTO.getUserRoleId());
        User user = userDTO.getUser();
        user.setUserRole(userRoleParser.getUserRole(userRole));
        return user;
    }
}
