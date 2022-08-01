package com.my.cmd.impl.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorUtility {
    public static final String ERROR_MESSAGE = "errorMessage";
    public void showError(HttpServletRequest request, HttpServletResponse response, String pageToRedirect, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        request.getRequestDispatcher(pageToRedirect).forward(request, response);
    }
}
