package com.my.web.captcha.container.impl;

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
    private final Map<String, String> captchaContainer;

    public CookieCaptchaContainer(){
        captchaContainer = new ConcurrentHashMap<>();
        random = new Random(RANDOM_SEED);
    }

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response, String captcha) {
        String key = Integer.toString(random.nextInt());
        Cookie cookie = new Cookie(CAPTCHA, key);
        response.addCookie(cookie);
        captchaContainer.put(key, captcha);
    }

    @Override
    public String get(HttpServletRequest request) throws CaptchaException {
        try{
            String captcha = null;
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(CAPTCHA)){
                    captcha = getCaptcha(cookie);
                }
            }
            return captcha;
        } catch (NullPointerException e){
            throw new CaptchaException(TIMEOUT_MESSAGE);
        }
    }

    @Override
    public void remove(HttpServletRequest request, String captcha) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(CAPTCHA)){
                captchaContainer.remove(cookie.getValue());
            }
        }
    }

    private String getCaptcha(Cookie cookie){
        String key = cookie.getValue();
        return captchaContainer.get(key);
    }
}
