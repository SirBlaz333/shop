package com.my.web.captcha.container;

import com.my.entity.Captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaContainerStrategy {
    String CAPTCHA_KEY = "captchaKey";
    void put(HttpServletRequest request, HttpServletResponse response, String captchaKey, Captcha captcha);
    Captcha get(HttpServletRequest request);
    void startRemoveRemove(String captchaKey);
}
