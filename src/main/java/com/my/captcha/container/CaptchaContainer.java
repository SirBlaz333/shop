package com.my.captcha.container;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaContainer {
    void put(HttpServletRequest request, HttpServletResponse response, String captcha);
    String get(HttpServletRequest request);
}
