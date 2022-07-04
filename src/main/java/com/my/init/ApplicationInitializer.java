package com.my.init;

import com.my.cmd.container.CommandContainer;
import com.my.dao.DBManager;
import com.my.dao.user.UserDAO;
import com.my.dao.user.impl.UserDAOImpl;
import com.my.service.user.UserService;
import com.my.service.user.UserServiceImpl;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategies;

public class ApplicationInitializer {
    private final UserDAO userDAO;
    private final UserService userService;
    private final long timeoutMillis;
    private final CaptchaContainerStrategies strategy;
    private final CommandContainer commandContainer;

    public ApplicationInitializer(){
        DBManager dbManager = new DBManager();
        userDAO = new UserDAOImpl(dbManager);
        userService = new UserServiceImpl(userDAO);
        timeoutMillis = 180_000;
        strategy = CaptchaContainerStrategies.COOKIE_CONTAINER;
        commandContainer = new CommandContainer(userService, strategy, timeoutMillis);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public UserService getUserService() {
        return userService;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public CaptchaContainerStrategies getStrategy() {
        return strategy;
    }

    public CommandContainer getCommandContainer() {
        return commandContainer;
    }
}
