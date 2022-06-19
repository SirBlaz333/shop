package com.my.login;

import com.my.captcha.container.CaptchaContainer;
import com.my.user.UserField;
import com.my.user.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.my.captcha.Captcha.CAPTCHA;
import static com.my.listener.ContextListener.CAPTCHA_CONTAINER;
import static com.my.listener.ContextListener.USER_SERVICE;
import static com.my.user.UserField.*;

@WebServlet(name = "Login",
        urlPatterns = {"/login/*"})
public class LoginServlet extends HttpServlet {
    public static final String USER_EXISTS_MESSAGE = "User with such email already exists";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String MAIN_PAGE = "index.html";
    public static final String REGISTRATION = "registration.jsp";
    public static final String WRONG_CAPTCHA_MESSAGE = "You enter wrong number. Please try again";
    public static final String TIMEOUT_MESSAGE = "Captcha expired. Please try again";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String expectedCaptcha = getCaptcha(request);
        String userCaptcha = request.getParameter(CAPTCHA);
        String email = request.getParameter(EMAIL.toString().toLowerCase());
        UserService userService = (UserService) getServletContext().getAttribute(USER_SERVICE);
        if(expectedCaptcha == null){
            showError(request, response, TIMEOUT_MESSAGE);
            return;
        }
        if(!userCaptcha.equals(expectedCaptcha)){
            showError(request, response, WRONG_CAPTCHA_MESSAGE);
            return;
        }
        if(userService.exists(email)){
            showError(request, response, USER_EXISTS_MESSAGE);
            return;
        }
        response.sendRedirect(MAIN_PAGE);
    }

    private String getCaptcha(HttpServletRequest request) {
        ServletContext servletContext = getServletContext();
        CaptchaContainer container = (CaptchaContainer) servletContext.getAttribute(CAPTCHA_CONTAINER);
        return container.get(request);
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        setAttributes(request);
        request.getRequestDispatcher(REGISTRATION).forward(request, response);
    }

    public void setAttributes(HttpServletRequest request){
        List<UserField> parameters = new ArrayList<>();
        parameters.add(EMAIL);
        parameters.add(FIRSTNAME);
        parameters.add(LASTNAME);
        for(UserField userField : parameters){
            String parameter = userField.toString().toLowerCase();
            request.setAttribute(parameter, request.getParameter(parameter));
        }
    }
}
