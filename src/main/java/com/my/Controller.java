package com.my;

import com.my.cmd.Command;
import com.my.cmd.container.CommandContainer;
import com.my.dao.user.UserDAOMap;
import com.my.service.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.web.captcha.container.impl.CaptchaContainerStrategy.COOKIE_CONTAINER;

@WebServlet(name = "Controller",
        urlPatterns = "/controller/*")
public class Controller extends HttpServlet {
    private CommandContainer container;

    @Override
    public void init() throws ServletException {
        super.init();
        setContainer(new CommandContainer(new UserServiceImpl(new UserDAOMap()),COOKIE_CONTAINER, 10000));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter("command");
        Command command = container.getCommand(commandName);
        command.doCommand(request, response);
    }

    public void setContainer(CommandContainer container) {
        this.container = container;
    }
}
