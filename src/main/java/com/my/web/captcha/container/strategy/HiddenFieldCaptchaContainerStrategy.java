package com.my.web.captcha.container.strategy;

import com.my.entity.Captcha;
import com.my.web.captcha.container.CaptchaContainerStrategy;
import com.my.web.captcha.container.impl.CaptchaContainerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HiddenFieldCaptchaContainerStrategy implements CaptchaContainerStrategy {
    private final CaptchaContainerImpl container;

    public HiddenFieldCaptchaContainerStrategy(long timeout){
        container = new CaptchaContainerImpl(timeout);
    }

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response, String captchaKey, Captcha captcha) {
        request.setAttribute(CAPTCHA_KEY, captchaKey);
        container.put(captchaKey, captcha);
    }

    @Override
    public Captcha get(HttpServletRequest request) {
        String captchaKey = request.getParameter(CAPTCHA_KEY);
        return container.get(captchaKey);
    }

    @Override
    public void startRemoveRemove(String captchaKey) {
        container.remoteRemove(captchaKey);
    }
}
