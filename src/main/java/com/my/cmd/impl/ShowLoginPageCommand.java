package com.my.cmd.impl;

import com.my.cmd.Method;
import com.my.cmd.impl.util.RedirectionUtility;
import com.my.entity.Captcha;
import com.my.service.captcha.CaptchaService;
import com.my.cmd.Command;
import com.my.util.LoggerFactory;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;
import com.my.web.page.Pages;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowLoginPageCommand implements Command {
    public final static String IMAGE_FORMAT = "png";
    public static final String CAPTCHA_IMAGE = "captchaImg";
    public static final String REGISTER = "register";
    public static final String CANNOT_DISPLAY_CAPTCHA = "Cannot display captcha";
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowLoginPageCommand.class);
    private final CaptchaService captchaService;
    private final CaptchaContainerStrategy container;

    public ShowLoginPageCommand(CaptchaContainerStrategy captchaContainerStrategy) {
        this.captchaService = new CaptchaService();
        this.container = captchaContainerStrategy;
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws IOException, ServletException {
        Object captcha = request.getSession().getAttribute(CAPTCHA_IMAGE);
        if(method == Method.POST || captcha == null){
            createCaptchaAndRedirect(request, response);
        }
        if(method == Method.GET && captcha != null){
            displayCaptcha(request, response);
        }
    }

    private void createCaptchaAndRedirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Captcha captcha = captchaService.createCaptcha();
        String captchaKey = captchaService.createCaptchaKey();
        container.put(request, response, captchaKey, captcha);
        container.startRemoveRemove(captchaKey);
        request.getSession().setAttribute(CAPTCHA_IMAGE, captcha.getImage());
        setAttributes(request);
        request.getRequestDispatcher(Pages.REGISTRATION_JSP).forward(request, response);
    }

    private void setAttributes(HttpServletRequest request){
        request.setAttribute(REGISTER, request.getParameter(REGISTER));
        if(request.getParameter(RedirectionUtility.FROM) != null){
            request.setAttribute(RedirectionUtility.FROM, request.getParameter(RedirectionUtility.FROM));
        }
        if(request.getParameter(RedirectionUtility.ERROR_MESSAGE) != null){
            request.setAttribute(RedirectionUtility.ERROR_MESSAGE, request.getParameter(RedirectionUtility.ERROR_MESSAGE));
        }
    }

    private void displayCaptcha(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        BufferedImage captchaImg = (BufferedImage) httpSession.getAttribute(CAPTCHA_IMAGE);
        writeImage(response, captchaImg);
    }

    private void writeImage(HttpServletResponse response, BufferedImage bufferedImage) {
        try{
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, IMAGE_FORMAT, outputStream);
        } catch (IOException e){
            LOGGER.log(Level.SEVERE, CANNOT_DISPLAY_CAPTCHA);
        }
    }
}
