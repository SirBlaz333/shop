package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.LoginUtility;
import com.my.entity.User;
import com.my.entity.UserRegFields;
import com.my.service.ServiceException;
import com.my.service.user.UserService;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;
import com.my.web.captcha.exception.CaptchaException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {
    private final UserService userService;
    private final LoginUtility loginUtility;

    public LoginCommand(CaptchaContainerStrategy captchaContainer, UserService userService) {
        this.userService = userService;
        loginUtility = new LoginUtility(captchaContainer);
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        try {
            doLogin(request, response);
        } catch (ServiceException | CaptchaException e) {
            loginUtility.showError(request, response, e.getMessage());
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServiceException, CaptchaException, IOException, ServletException {
        loginUtility.checkCaptcha(request);
        User user = loginUtility.createUser(request);
        user = userService.login(user);
        request.getSession().setAttribute(UserRegFields.USER, user);
        response.sendRedirect(LoginUtility.MAIN_PAGE);
    }
}
