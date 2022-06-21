package com.my.web;

import com.my.web.captcha.CaptchaContainer;
import com.my.user.dao.UserDAO;
import com.my.user.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.reflect.InvocationTargetException;

@WebListener
public class ContextListener implements ServletContextListener {
    public static final String CAPTCHA_CONTAINER = "CaptchaContainer";
    public static final String TIMEOUT = "Timeout";
    public static final String USER_SERVICE = "UserService";
    public static final String USER_DAO = "UserDAO";

    private ServletContext servletContext;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
         servletContext = sce.getServletContext();
        try{
            initCaptchaContainer();
            initUserService();
            initTimeout();
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void initCaptchaContainer() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String className = servletContext.getInitParameter(CAPTCHA_CONTAINER);
        Class<?> containerClass = Class.forName(className);
        CaptchaContainer container = (CaptchaContainer) containerClass.getDeclaredConstructor().newInstance();
        servletContext.setAttribute(CAPTCHA_CONTAINER, container);
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

    private void initTimeout(){
        String timeout = servletContext.getInitParameter(TIMEOUT);
        servletContext.setAttribute(TIMEOUT, Long.parseLong(timeout));
    }
}
