package com.my.cmd.impl.util;

import com.my.entity.Captcha;
import com.my.entity.User;
import com.my.entity.UserBuilder;
import com.my.web.captcha.exception.CaptchaException;

import javax.servlet.http.HttpServletRequest;

import static com.my.entity.UserRegFields.*;
import static com.my.entity.UserRegFields.LASTNAME;

public class RegistrationUtility {
    public static final String TIMEOUT_MESSAGE = "Captcha expired. Please try again";
    public static final String WRONG_CAPTCHA_MESSAGE = "You enter wrong number. Please try again";

    public void setAttributesForForward(HttpServletRequest request){
        request.setAttribute(EMAIL, request.getParameter(EMAIL));
        request.setAttribute(FIRSTNAME, request.getParameter(FIRSTNAME));
        request.setAttribute(LASTNAME, request.getParameter(LASTNAME));
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

    public void checkCaptcha(Captcha expectedCaptcha, String actualCaptcha) throws CaptchaException {
        if(expectedCaptcha == null){
            throw new CaptchaException(TIMEOUT_MESSAGE);
        }
        if(!expectedCaptcha.getText().equals(actualCaptcha)){
            throw new CaptchaException(WRONG_CAPTCHA_MESSAGE);
        }
    }
}
