package com.my.web.captcha;

import com.my.web.captcha.Captcha;
import com.my.web.captcha.CaptchaContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionCaptchaContainer implements CaptchaContainer {

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response, String captcha) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(Captcha.CAPTCHA, captcha);
    }

    @Override
    public String get(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        return (String) httpSession.getAttribute(Captcha.CAPTCHA);
    }

    @Override
    public void remove(HttpSession httpSession, String captcha) {
        httpSession.removeAttribute(Captcha.CAPTCHA);
    }
}
