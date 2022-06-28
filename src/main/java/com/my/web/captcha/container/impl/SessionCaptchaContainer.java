package com.my.web.captcha.container.impl;

import com.my.entity.Captcha;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.web.captcha.exception.CaptchaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.entity.UserRegFields.CAPTCHA;

public class SessionCaptchaContainer implements CaptchaContainer {

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response, Captcha captcha) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(CAPTCHA, captcha);
    }

    @Override
    public Captcha getWithTimeout(HttpServletRequest request, int timeout) throws CaptchaException {
        HttpSession httpSession = request.getSession();
        Captcha captcha = (Captcha) httpSession.getAttribute(CAPTCHA);
        if(captcha == null || captcha.isExpired(timeout)){
            throw new CaptchaException(TIMEOUT_MESSAGE);
        }
        return captcha;
    }
}
