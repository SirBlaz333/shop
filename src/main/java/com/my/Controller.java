package com.my;

import com.my.cmd.Command;
import com.my.cmd.container.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller",
        urlPatterns = "/controller/*")
public class Controller extends HttpServlet {
    private CommandContainer container;

    @Override
    public void init() throws ServletException {
        super.init();
        container = new CommandContainer();
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
}
