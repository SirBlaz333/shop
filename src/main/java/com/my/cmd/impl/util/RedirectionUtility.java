package com.my.cmd.impl.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectionUtility {
    public static final String FROM = "from";
    public static final String ERROR_MESSAGE = "errorMessage";

    public void showErrorRedirect(HttpServletResponse response, String pageToRedirect, String errorMessage) throws IOException {
        String url = appendErrorMessage(pageToRedirect, errorMessage);
        response.sendRedirect(url);
    }

    public void showError(HttpServletRequest request, HttpServletResponse response, String pageToRedirect, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        request.getRequestDispatcher(pageToRedirect).forward(request, response);
    }

    public String getRedirectUrl(HttpServletRequest request, String defaultURL) {
        String redirectURL = request.getParameter(FROM);
        return (redirectURL != null) ? redirectURL : defaultURL;
    }

    private String appendErrorMessage(String URL, String paramValue) {
        return URL + RedirectionUtility.ERROR_MESSAGE + "=" + paramValue + "&";
    }
}
