package com.my.cmd.impl;

import com.my.cmd.Method;
import com.my.entity.Captcha;
import com.my.service.captcha.CaptchaService;
import com.my.web.captcha.container.CaptchaContainerStrategy;
import com.my.cmd.Command;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class ShowLoginPageCommand implements Command {
    public static final String REGISTRATION = "registration.jsp";
    public final static String IMAGE_FORMAT = "png";
    public static final String CAPTCHA_IMAGE = "captchaImg";
    public static final String REGISTER = "register";
    private final CaptchaService captchaService;
    private final CaptchaContainerStrategy container;

    public ShowLoginPageCommand(CaptchaContainerStrategy captchaContainerStrategy) {
        captchaService = new CaptchaService();
        container = captchaContainerStrategy;
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws IOException, ServletException {
        if(method == Method.GET){
            displayCaptcha(request, response);
        }
        if(method == Method.POST){
            createCaptchaAndRedirect(request, response);
        }
    }

    private void createCaptchaAndRedirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Captcha captcha = captchaService.createCaptcha();
        String captchaKey = captchaService.createCaptchaKey();
        container.put(request, response, captchaKey, captcha);
        container.startRemoveRemove(captchaKey);
        request.getSession().setAttribute(CAPTCHA_IMAGE, captcha.getImage());
        request.setAttribute(REGISTER, request.getParameter(REGISTER));
        request.getRequestDispatcher(REGISTRATION).forward(request, response);
    }

    private void displayCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession();
        BufferedImage captchaImg = (BufferedImage) httpSession.getAttribute(CAPTCHA_IMAGE);
        writeImage(response, captchaImg);
    }

    private void writeImage(HttpServletResponse response, BufferedImage bufferedImage) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, IMAGE_FORMAT, outputStream);
    }
}
