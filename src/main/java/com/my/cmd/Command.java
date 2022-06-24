package com.my.cmd;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    void doCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
