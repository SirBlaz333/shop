package com.my.cmd.impl;

import com.my.entity.Captcha;
import com.my.service.captcha.CaptchaService;
import com.my.web.captcha.container.CaptchaContainerStrategy;
import com.my.cmd.Command;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static com.my.cmd.impl.DisplayCaptchaCommand.CAPTCHA_IMAGE;

public class ShowRegistrationPageCommand implements Command {
    public static final String REGISTRATION = "registration.jsp";
    private final CaptchaService captchaService;
    private final CaptchaContainerStrategy container;

    public ShowRegistrationPageCommand(CaptchaContainerStrategy captchaContainerStrategy) {
        captchaService = new CaptchaService();
        container = captchaContainerStrategy;
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Captcha captcha = captchaService.createCaptcha();
        String captchaKey = captchaService.createCaptchaKey();
        container.put(request, response, captchaKey, captcha);
        container.startRemoveRemove(captchaKey);
        request.getSession().setAttribute(CAPTCHA_IMAGE, captcha.getImage());
        request.getRequestDispatcher(REGISTRATION).forward(request, response);
    }
}
