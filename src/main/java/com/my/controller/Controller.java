package com.my.controller;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.container.CommandContainer;
import com.my.init.ApplicationInitializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller",
        urlPatterns = "/controller/*")
public class Controller extends HttpServlet {
    private CommandContainer commandContainer;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationInitializer applicationInitializer = new ApplicationInitializer();
        setCommandContainer(applicationInitializer.getCommandContainer());
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
