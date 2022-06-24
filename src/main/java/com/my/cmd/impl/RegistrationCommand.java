package com.my.cmd.impl;

import com.my.cmd.impl.util.RegistrationUtility;
import com.my.entity.User;
import com.my.service.ServiceException;
import com.my.web.captcha.exception.CaptchaException;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.service.user.UserService;
import com.my.cmd.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.entity.UserRegFields.*;
import static com.my.web.ContextListener.USER_SERVICE;

public class RegistrationCommand implements Command {
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String MAIN_PAGE = "index.html";
    public static final String REGISTRATION = "registration.jsp";
    private final CaptchaContainer container;
    private final RegistrationUtility registrationUtility;

    public RegistrationCommand(CaptchaContainer captchaContainer){
        container = captchaContainer;
        registrationUtility = new RegistrationUtility();
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doLogin(request, response);
        } catch (ServiceException | CaptchaException e) {
            showError(request, response, e.getMessage());
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws CaptchaException, ServiceException, IOException {
        String expectedCaptcha = container.get(request);
        String userCaptcha = request.getParameter(CAPTCHA);
        UserService userService = (UserService) request.getServletContext().getAttribute(USER_SERVICE);
        User user = registrationUtility.createUser(request);
        registrationUtility.checkCaptcha(expectedCaptcha, userCaptcha);
        userService.add(user);
        response.sendRedirect(MAIN_PAGE);
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        registrationUtility.setAttributesForForward(request);
        request.getRequestDispatcher(REGISTRATION).forward(request, response);
    }
}
