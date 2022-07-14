package com.my.web.captcha.container.strategy.impl;

import com.my.entity.Captcha;
import com.my.web.captcha.container.impl.CaptchaContainerImpl;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionCaptchaContainerStrategy implements CaptchaContainerStrategy {

    private final CaptchaContainerImpl container;
    public SessionCaptchaContainerStrategy(long timeout){
        container = new CaptchaContainerImpl(timeout);
    }

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response, String captchaKey, Captcha captcha) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(CAPTCHA_KEY, captchaKey);
        container.put(captchaKey, captcha);
    }

    @Override
    public Captcha get(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        String captchaKey = (String) httpSession.getAttribute(CAPTCHA_KEY);
        return container.get(captchaKey);
    }

    @Override
    public void startRemoveRemove(String captchaKey) {
        container.remoteRemove(captchaKey);
    }
}
