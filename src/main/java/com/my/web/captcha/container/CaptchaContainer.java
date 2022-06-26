package com.my.web.captcha.container;

import com.my.web.captcha.exception.CaptchaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaContainer {
    String TIMEOUT_MESSAGE = "Captcha expired. Please try again";
    void put(HttpServletRequest request, HttpServletResponse response, String captcha);
    String get(HttpServletRequest request) throws CaptchaException;
    void remove(HttpServletRequest request, String captcha);
}
