package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.LoginUtility;
import com.my.entity.User;
import com.my.service.ServiceException;
import com.my.service.user.UserService;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.web.captcha.container.CaptchaContainerStrategy;
import com.my.web.captcha.exception.CaptchaException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.cmd.impl.util.LoginUtility.MAIN_PAGE;

public class LoginCommand implements Command {
    private final UserService userService;
    private final LoginUtility loginUtility;

    public LoginCommand(CaptchaContainerStrategy captchaContainer, UserService userService, ShowLoginPageCommand showLoginPageCommand) {
        this.userService = userService;
        loginUtility = new LoginUtility(showLoginPageCommand, captchaContainer);
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        try {
            doLogin(request, response);
        } catch (ServiceException | CaptchaException e) {
            loginUtility.showError(request, response, e.getMessage());
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServiceException, CaptchaException, IOException {
        loginUtility.checkCaptcha(request);
        User user = loginUtility.createUser(request);
        user = userService.login(user);
        request.getSession().setAttribute("user", user);
        response.sendRedirect(MAIN_PAGE);
    }
}
