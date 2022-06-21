package com.my.web;

import com.my.web.command.WebCommand;
import com.my.web.command.WebCommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller",
        urlPatterns = "/controller/*")
public class Controller extends HttpServlet {

    private WebCommandContainer container;

    @Override
    public void init() throws ServletException {
        super.init();
        container = new WebCommandContainer();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter("command");
        WebCommand webCommand = container.getCommand(commandName);
        webCommand.doCommand(request, response);
    }
}
