package com.my.cmd.impl;

import com.my.cmd.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.cmd.impl.util.LoginUtility.MAIN_PAGE;

public class LogoutCommand implements Command {
    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(MAIN_PAGE);
    }
}
