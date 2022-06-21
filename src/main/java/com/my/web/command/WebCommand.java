package com.my.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface WebCommand {
    void doCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
