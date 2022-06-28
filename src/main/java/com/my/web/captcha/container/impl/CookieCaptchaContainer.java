package com.my.web.captcha.container.impl;

import com.my.entity.Captcha;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.web.captcha.exception.CaptchaException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static com.my.entity.UserRegFields.CAPTCHA;

public class CookieCaptchaContainer implements CaptchaContainer {
    private static final long RANDOM_SEED = 777_777;
    private final Random random;
    private final Map<String, Captcha> captchaContainer;
    private int timeout;

    public CookieCaptchaContainer(){
        captchaContainer = new ConcurrentHashMap<>();
        random = new Random(RANDOM_SEED);
    }

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response, Captcha captcha) {
        String key = Integer.toString(random.nextInt());
        Cookie cookie = new Cookie(CAPTCHA, key);
        response.addCookie(cookie);
        captchaContainer.put(key, captcha);
    }

    @Override
    public Captcha getWithTimeout(HttpServletRequest request, int timeout) throws CaptchaException {
        Captcha captcha = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(CAPTCHA)){
                captcha = getCaptcha(cookie);
            }
        }
        if(captcha == null || captcha.isExpired(timeout)){
            throw new CaptchaException(TIMEOUT_MESSAGE);
        }
        return captcha;
    }

    private Captcha getCaptcha(Cookie cookie){
        String key = cookie.getValue();
        return captchaContainer.get(key);
    }
}
