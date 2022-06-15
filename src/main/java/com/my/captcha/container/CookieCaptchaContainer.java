package com.my.captcha.container;

import com.my.captcha.Captcha;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class CookieCaptchaContainer implements CaptchaContainer, Captcha {

    private HashMap<String, String> captchaContainer;

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response, String captcha) {
        String hashedCaptcha = Integer.toString(captcha.hashCode());
        Cookie cookie = new Cookie(CAPTCHA, hashedCaptcha);
        response.addCookie(cookie);
        captchaContainer.put(hashedCaptcha, captcha);
    }

    @Override
    public String get(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(CAPTCHA)){
                return getCaptcha(cookie);
            }
        }
        return null;
    }

    private String getCaptcha(Cookie cookie){
        String hashedCaptcha = cookie.getValue();
        return captchaContainer.get(hashedCaptcha);
    }
}
