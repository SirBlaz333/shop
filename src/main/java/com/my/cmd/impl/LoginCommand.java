package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.RedirectionUtility;
import com.my.cmd.impl.util.LoginUtility;
import com.my.entity.user.User;
import com.my.entity.user.UserRegFields;
import com.my.service.ServiceException;
import com.my.service.user.UserService;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;
import com.my.web.captcha.exception.CaptchaException;
import com.my.web.page.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {
    private final UserService userService;
    private final LoginUtility loginUtility;
    private final RedirectionUtility redirectionUtility;

    public LoginCommand(CaptchaContainerStrategy captchaContainer, UserService userService) {
        this.userService = userService;
        this.loginUtility = new LoginUtility(captchaContainer);
        this.redirectionUtility = new RedirectionUtility();
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        try {
            doLogin(request, response);
        } catch (ServiceException | CaptchaException e) {
            loginUtility.setAttributesForForward(request);
            redirectionUtility.showError(request, response, Pages.LOGIN, e.getMessage());
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServiceException, CaptchaException, IOException, ServletException {
        loginUtility.checkCaptcha(request);
        User user = loginUtility.createUser(request);
        user = userService.login(user);
        request.getSession().setAttribute(UserRegFields.USER, user);
        String url = redirectionUtility.getRedirectUrl(request, Pages.MAIN);
        response.sendRedirect(url);
    }
}
