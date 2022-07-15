package com.my.cmd.impl;

import com.my.cmd.impl.util.LoginUtility;
import com.my.cmd.Method;
import com.my.entity.User;
import com.my.service.ServiceException;
import com.my.web.captcha.exception.CaptchaException;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;
import com.my.service.user.UserService;
import com.my.cmd.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.cmd.impl.util.LoginUtility.MAIN_PAGE;

public class RegistrationCommand implements Command {
    private final LoginUtility loginUtility;
    private final UserService userService;

    public RegistrationCommand(CaptchaContainerStrategy captchaContainer, UserService userService, ShowLoginPageCommand showLoginPageCommand) {
        this.userService = userService;
        loginUtility = new LoginUtility(showLoginPageCommand, captchaContainer);
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        try {
            doRegister(request, response);
        } catch (ServiceException | CaptchaException e) {
            loginUtility.showError(request, response, e.getMessage());
        }
    }
    private void doRegister(HttpServletRequest request, HttpServletResponse response) throws CaptchaException, ServiceException, IOException {
        loginUtility.checkCaptcha(request);
        User user = loginUtility.createUser(request);
        user = userService.add(user);
        loginUtility.attachAndRenameImageInFolder(request.getSession(), user);
        request.getSession().setAttribute("user", user);
        response.sendRedirect(MAIN_PAGE);
    }
}
