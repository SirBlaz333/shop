package com.my.cmd.impl.util;

import com.my.cmd.Method;
import com.my.cmd.impl.DisplayAvatarCommand;
import com.my.cmd.impl.ShowLoginPageCommand;
import com.my.entity.Captcha;
import com.my.entity.User;
import com.my.entity.UserBuilder;
import com.my.entity.UserRegFields;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;
import com.my.web.captcha.exception.CaptchaException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class LoginUtility {
    public static final String MAIN_PAGE = "index.jsp";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String TIMEOUT_MESSAGE = "Captcha expired. Please try again";
    public static final String WRONG_CAPTCHA_MESSAGE = "You enter wrong number. Please try again";
    private final ShowLoginPageCommand showLoginPageCommand;
    private final CaptchaContainerStrategy container;

    public LoginUtility(ShowLoginPageCommand showLoginPageCommand, CaptchaContainerStrategy captchaContainer){
        this.showLoginPageCommand = showLoginPageCommand;
        container = captchaContainer;
    }

    public void checkCaptcha(HttpServletRequest request) throws CaptchaException {
        Captcha expectedCaptcha = container.get(request);
        String userCaptcha = request.getParameter(UserRegFields.CAPTCHA);
        checkCaptcha(expectedCaptcha, userCaptcha);
    }

    public User createUser(HttpServletRequest request) throws ServletException {
        String email = request.getParameter(UserRegFields.EMAIL);
        String firstname = request.getParameter(UserRegFields.FIRSTNAME);
        String lastname = request.getParameter(UserRegFields.LASTNAME);
        String password = request.getParameter(UserRegFields.PASSWORD);
        String newsletterParameter = request.getParameter(UserRegFields.NEWSLETTER);
        boolean newsletter = (newsletterParameter != null);
        BufferedImage bufferedImage = uploadAvatar(request);
        return new UserBuilder().
                withEmail(email).
                withFirstname(firstname).
                withLastname(lastname).
                withPassword(password).
                withNewsletter(newsletter).
                withImage(bufferedImage).
                getUser();
    }

    private BufferedImage uploadAvatar(HttpServletRequest request) throws ServletException {
        try{
            Part part = request.getPart(UserRegFields.AVATAR);
            InputStream inputStream = part.getInputStream();
            return ImageIO.read(inputStream);
        } catch (IOException | NullPointerException e){
            return null;
        }
    }

    private void checkCaptcha(Captcha expectedCaptcha, String actualCaptcha) throws CaptchaException {
        if(expectedCaptcha == null){
            throw new CaptchaException(TIMEOUT_MESSAGE);
        }
        if(!expectedCaptcha.getText().equals(actualCaptcha)){
            throw new CaptchaException(WRONG_CAPTCHA_MESSAGE);
        }
    }


    public void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        setAttributesForForward(request);
        showLoginPageCommand.doCommand(request, response, Method.POST);
    }

    private void setAttributesForForward(HttpServletRequest request){
        request.setAttribute(UserRegFields.EMAIL, request.getParameter(UserRegFields.EMAIL));
        request.setAttribute(UserRegFields.FIRSTNAME, request.getParameter(UserRegFields.FIRSTNAME));
        request.setAttribute(UserRegFields.LASTNAME, request.getParameter(UserRegFields.LASTNAME));
    }
}
