package com.my.cmd.impl;

import com.my.cmd.impl.util.RegistrationUtility;
import com.my.entity.Captcha;
import com.my.entity.User;
import com.my.service.ServiceException;
import com.my.web.captcha.exception.CaptchaException;
import com.my.web.captcha.container.CaptchaContainerStrategy;
import com.my.service.user.UserService;
import com.my.cmd.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.entity.UserRegFields.*;

public class RegistrationCommand implements Command {
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String MAIN_PAGE = "index.html";
    private final CaptchaContainerStrategy container;
    private final RegistrationUtility registrationUtility;
    private final UserService userService;
    private final ShowRegistrationPageCommand showRegistrationPageCommand;

    public RegistrationCommand(CaptchaContainerStrategy captchaContainerStrategy, UserService userService, ShowRegistrationPageCommand showRegistrationPageCommand){
        container = captchaContainerStrategy;
        this.userService = userService;
        registrationUtility = new RegistrationUtility();
        this.showRegistrationPageCommand = showRegistrationPageCommand;
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
        Captcha expectedCaptcha = container.get(request);
        String userCaptcha = request.getParameter(CAPTCHA);
        User user = registrationUtility.createUser(request);
        registrationUtility.checkCaptcha(expectedCaptcha, userCaptcha);
        userService.add(user);
        response.sendRedirect(MAIN_PAGE);
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        registrationUtility.setAttributesForForward(request);
        showRegistrationPageCommand.doCommand(request, response);
    }
}
