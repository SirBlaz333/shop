package com.my.web.captcha.container.impl;

import com.my.web.captcha.container.CaptchaContainer;

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
    public String get(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        return (String) httpSession.getAttribute(CAPTCHA);
    }

    @Override
    public void remove(HttpSession httpSession, String captcha) {
        httpSession.removeAttribute(CAPTCHA);
    }
}
