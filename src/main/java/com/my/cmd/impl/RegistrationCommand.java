package com.my.cmd.impl;

import com.my.cmd.impl.util.ErrorUtility;
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
    public static final String REGISTRATION_PAGE = "controller?command=showLoginPage&register=true";
    private final LoginUtility loginUtility;
    private final UserService userService;
    private final ErrorUtility errorUtility;

    public RegistrationCommand(CaptchaContainerStrategy captchaContainer, UserService userService) {
        this.userService = userService;
        loginUtility = new LoginUtility(captchaContainer);
        errorUtility = new ErrorUtility();
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        try {
            doRegister(request, response);
        } catch (ServiceException | CaptchaException e) {
            loginUtility.setAttributesForForward(request);
            errorUtility.showError(request, response, REGISTRATION_PAGE, e.getMessage());
        }
    }

    private void doRegister(HttpServletRequest request, HttpServletResponse response) throws CaptchaException, ServiceException, IOException, ServletException {
        loginUtility.checkCaptcha(request);
        User user = loginUtility.createUser(request);
        String imagesFilepath = request.getServletContext().getInitParameter(DisplayAvatarCommand.IMAGES_FILEPATH);
        user = userService.add(user, imagesFilepath);
        request.getSession().setAttribute(UserRegFields.USER, user);
        response.sendRedirect(Pages.MAIN);
    }
}
