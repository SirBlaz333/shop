package com.my.cmd.impl;

import com.my.cmd.impl.util.RedirectionUtility;
import com.my.cmd.impl.util.LoginUtility;
import com.my.cmd.Method;
import com.my.entity.User;
import com.my.entity.UserRegFields;
import com.my.service.ServiceException;
import com.my.web.captcha.exception.CaptchaException;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;
import com.my.service.user.UserService;
import com.my.cmd.Command;
import com.my.web.page.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {
    private final LoginUtility loginUtility;
    private final UserService userService;
    private final RedirectionUtility redirectionUtility;

    public RegistrationCommand(CaptchaContainerStrategy captchaContainer, UserService userService) {
        this.userService = userService;
        loginUtility = new LoginUtility(captchaContainer);
        redirectionUtility = new RedirectionUtility();
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        try {
            doRegister(request, response);
        } catch (ServiceException | CaptchaException e) {
            loginUtility.setAttributesForForward(request);
            redirectionUtility.setRedirectUrl(request);
            redirectionUtility.showError(request, response, Pages.REGISTRATION, e.getMessage());
        }
    }

    private void doRegister(HttpServletRequest request, HttpServletResponse response) throws CaptchaException, ServiceException, IOException, ServletException {
        loginUtility.checkCaptcha(request);
        User user = loginUtility.createUser(request);
        String imagesFilepath = request.getServletContext().getInitParameter(DisplayAvatarCommand.IMAGES_FILEPATH);
        user = userService.add(user, imagesFilepath);
        request.getSession().setAttribute(UserRegFields.USER, user);
        String url = redirectionUtility.getRedirectUrl(request, Pages.MAIN);
        response.sendRedirect(url);
    }
}
