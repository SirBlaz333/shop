package com.my.web.captcha.container;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface CaptchaContainer {
    void put(HttpServletRequest request, HttpServletResponse response, String captcha);
    String get(HttpServletRequest request);
    void remove(HttpSession httpSession, String captcha);
}
