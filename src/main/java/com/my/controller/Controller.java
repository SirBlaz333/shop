package com.my.controller;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.container.CommandContainer;
import com.my.dao.user.UserDAO;
import com.my.dao.user.UserDAOMap;
import com.my.service.user.UserService;
import com.my.service.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.web.captcha.container.strategy.CaptchaContainerStrategies.*;

@WebServlet(name = "Controller",
        urlPatterns = "/controller/*")
public class Controller extends HttpServlet {
    private static final int TIMEOUT = 10_000;
    private CommandContainer commandContainer;

    @Override
    public void init() throws ServletException {
        super.init();
        UserDAO userDAO = new UserDAOMap();
        UserService userService = new UserServiceImpl(userDAO);
        CommandContainer cmdContainer = new CommandContainer(userService, HIDDEN_FIELD_CONTAINER, TIMEOUT);
        setCommandContainer(cmdContainer);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doCommand(request, response, Method.GET);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doCommand(request, response, Method.POST);
    }

    private void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        String commandName = request.getParameter("command");
        Command command = commandContainer.getCommand(commandName);
        command.doCommand(request, response, method);
    }

    public void setCommandContainer(CommandContainer commandContainer) {
        this.commandContainer = commandContainer;
    }
}
