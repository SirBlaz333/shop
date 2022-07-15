package com.my.web.captcha.container.strategy.impl;

import com.my.entity.Captcha;
import com.my.web.captcha.container.impl.CaptchaContainerImpl;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieCaptchaContainerStrategy implements CaptchaContainerStrategy {
    private final CaptchaContainerImpl container;

    public CookieCaptchaContainerStrategy(long timeout){
        container = new CaptchaContainerImpl(timeout);
    }

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response, String captchaKey,  Captcha captcha) {
        Cookie cookie = new Cookie(CAPTCHA_KEY, captchaKey);
        response.addCookie(cookie);
        container.put(captchaKey, captcha);
    }

    @Override
    public Captcha get(HttpServletRequest request) {
        Captcha captcha = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(CAPTCHA_KEY)){
                captcha = getCaptcha(cookie);
            }
        }
        return captcha;
    }

    private Captcha getCaptcha(Cookie cookie){
        String key = cookie.getValue();
        return container.get(key);
    }

    @Override
    public void startRemoveRemove(String captchaKey) {
        container.remoteRemove(captchaKey);
    }
}
