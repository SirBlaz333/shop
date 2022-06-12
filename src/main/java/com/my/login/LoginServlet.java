package com.my.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.user.UserField.EMAIL;

@WebServlet(name = "Login",
        urlPatterns = {"/login/*"})
public class LoginServlet extends HttpServlet {
    public static final String USER_EXISTS = "User with such email already exists";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String MAIN_PAGE = "index.html";
    public static final String REGISTRATION = "registration.jsp";
    private final LoginUtility loginUtility;

    public LoginServlet() {
        loginUtility = new LoginUtility();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(EMAIL.toString().toLowerCase());
        if(loginUtility.userExists(email)){
            request.setAttribute(ERROR_MESSAGE, USER_EXISTS);
            loginUtility.setAttributes(request);
            request.getRequestDispatcher(REGISTRATION).forward(request, response);
        } else {
            response.sendRedirect(MAIN_PAGE);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


}
