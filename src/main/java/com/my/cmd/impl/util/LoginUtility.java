package com.my.cmd.impl.util;

import com.my.cmd.impl.ShowLoginPageCommand;
import com.my.entity.Captcha;
import com.my.entity.User;
import com.my.entity.UserBuilder;
import com.my.service.ServiceException;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.web.captcha.exception.CaptchaException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.my.entity.UserRegFields.*;
import static com.my.entity.UserRegFields.LASTNAME;

public class LoginUtility {
    public static final String MAIN_PAGE = "index.jsp";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String WRONG_CAPTCHA_MESSAGE = "You enter wrong number. Please try again";
    private final ShowLoginPageCommand showLoginPageCommand;
    private final CaptchaContainer container;
    private final int timeout;

    public LoginUtility(ShowLoginPageCommand showLoginPageCommand, CaptchaContainer captchaContainer, int timeout){
        this.showLoginPageCommand = showLoginPageCommand;
        container = captchaContainer;
        this.timeout = timeout;
    }

    public void checkCaptcha(HttpServletRequest request) throws ServiceException, CaptchaException, IOException {
        Captcha expectedCaptcha = container.getWithTimeout(request, timeout);
        String userCaptcha = request.getParameter(CAPTCHA);
        checkCaptcha(expectedCaptcha.getText(), userCaptcha);
    }

    public User createUser(HttpServletRequest request){
        String email = request.getParameter(EMAIL);
        String firstname = request.getParameter(FIRSTNAME);
        String lastname = request.getParameter(LASTNAME);
        String password = request.getParameter(PASSWORD);
        String newsletterParameter = request.getParameter(NEWSLETTER);
        boolean newsletter = (newsletterParameter != null);
        return new UserBuilder().
                withEmail(email).
                withFirstname(firstname).
                withLastname(lastname).
                withPassword(password).
                withNewsletter(newsletter)
                .getUser();
    }

    private void checkCaptcha(String expectedCaptcha, String actualCaptcha) throws CaptchaException {
        if(!expectedCaptcha.equals(actualCaptcha)){
            throw new CaptchaException(WRONG_CAPTCHA_MESSAGE);
        }
    }


    public void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        setAttributesForForward(request);
        showLoginPageCommand.doCommand(request, response);
    }

    private void setAttributesForForward(HttpServletRequest request){
        request.setAttribute(EMAIL, request.getParameter(EMAIL));
        request.setAttribute(FIRSTNAME, request.getParameter(FIRSTNAME));
        request.setAttribute(LASTNAME, request.getParameter(LASTNAME));
    }
}
