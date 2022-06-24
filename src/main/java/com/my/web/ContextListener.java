package com.my.web;

import com.my.dao.user.UserDAO;
import com.my.service.user.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.reflect.InvocationTargetException;

@WebListener
public class ContextListener implements ServletContextListener {
    public static final String USER_SERVICE = "UserService";
    public static final String USER_DAO = "UserDAO";
    private ServletContext servletContext;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
         servletContext = sce.getServletContext();
        try{
            initUserService();
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private void initUserService() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        UserDAO userDAO = getUserDAO();
        String className = servletContext.getInitParameter(USER_SERVICE);
        Class<?> userServiceClass = Class.forName(className);
        UserService userService = (UserService) userServiceClass.getDeclaredConstructor(UserDAO.class).newInstance(userDAO);
        servletContext.setAttribute(USER_SERVICE, userService);
    }

    private UserDAO getUserDAO() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String className = servletContext.getInitParameter(USER_DAO);
        Class<?> userDAOClass = Class.forName(className);
        return (UserDAO) userDAOClass.getDeclaredConstructor().newInstance();
    }
}
