package com.my.web.captcha.container.impl;

import com.my.entity.Captcha;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.web.captcha.exception.CaptchaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class HiddenFieldCaptchaContainer implements CaptchaContainer {
    private static final String CAPTCHA_KEY = "captchaKey";
    private static final long RANDOM_SEED = 123_123;
    private final Map<String, Captcha> captchaContainer;
    private final Random random;

    public HiddenFieldCaptchaContainer(){
        captchaContainer = new ConcurrentHashMap<>();
        random = new Random(RANDOM_SEED);
    }

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response, Captcha captcha) {
        String captchaKey = Integer.toString(random.nextInt());
        request.setAttribute(CAPTCHA_KEY, captchaKey);
        captchaContainer.put(captchaKey, captcha);
    }

    @Override
    public Captcha getWithTimeout(HttpServletRequest request, int timeout) throws CaptchaException {
        String captchaKey = request.getParameter(CAPTCHA_KEY);
        Captcha captcha = captchaContainer.get(captchaKey);
        if(captcha == null || captcha.isExpired(timeout)){
            throw new CaptchaException(TIMEOUT_MESSAGE);
        }
        return captcha;
    }
}
