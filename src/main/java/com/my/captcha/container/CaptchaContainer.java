package com.my.captcha.container;

import com.my.captcha.Captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaContainer extends Captcha {
    void put(HttpServletRequest request, HttpServletResponse response, String captcha);
    String get(HttpServletRequest request);
    void remove(HttpServletRequest request);
}
