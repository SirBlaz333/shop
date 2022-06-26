package com.my.web.captcha.container.impl;

import com.my.web.captcha.container.CaptchaContainer;
import com.my.web.captcha.exception.CaptchaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.entity.UserRegFields.CAPTCHA;

public class SessionCaptchaContainer implements CaptchaContainer {

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response, String captcha) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(CAPTCHA, captcha);
    }

    @Override
    public String get(HttpServletRequest request) throws CaptchaException {
        HttpSession httpSession = request.getSession();
        String captcha = (String) httpSession.getAttribute(CAPTCHA);
        if(captcha == null){
            throw new CaptchaException(TIMEOUT_MESSAGE);
        }
        return captcha;
    }

    @Override
    public void remove(HttpServletRequest request, String captcha) {
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute(CAPTCHA);
    }
}
