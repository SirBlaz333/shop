package com.my.controller;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.container.CommandContainer;
import com.my.init.ApplicationInitializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@MultipartConfig
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
        if(commandName == null){
            commandName = getCommandFromMultipartRequest(request);
        }
        Command command = commandContainer.getCommand(commandName);
        command.doCommand(request, response, method);
    }

    private String getCommandFromMultipartRequest(HttpServletRequest request) throws ServletException, IOException {
        Part part = request.getPart("command");
        InputStream inputStream = part.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        return scanner.nextLine();
    }

    public void setCommandContainer(CommandContainer commandContainer) {
        this.commandContainer = commandContainer;
    }
}
