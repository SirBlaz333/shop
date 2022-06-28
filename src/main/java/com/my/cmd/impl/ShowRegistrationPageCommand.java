package com.my.cmd.impl;

import com.my.entity.Captcha;
import com.my.cmd.impl.util.CaptchaUtility;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.cmd.Command;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static com.my.cmd.impl.DisplayCaptchaCommand.CAPTCHA_IMAGE;

public class ShowRegistrationPageCommand implements Command {
    public static final String REGISTRATION = "registration.jsp";
    private final CaptchaUtility captchaUtility;
    private final CaptchaContainer container;

    public ShowRegistrationPageCommand(CaptchaContainer captchaContainer) {
        captchaUtility = new CaptchaUtility();
        container = captchaContainer;
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Captcha captcha = captchaUtility.createCaptcha();
        container.put(request, response, captcha);
        request.getSession().setAttribute(CAPTCHA_IMAGE, captcha.getImage());
        request.getRequestDispatcher(REGISTRATION).forward(request, response);
    }
}
