package com.my.web.captcha.container.impl;

import com.my.web.captcha.container.CaptchaContainer;

public class CaptchaContainerStrategy {
    public static final CaptchaContainer COOKIE_CONTAINER = new CookieCaptchaContainer();
    public static final CaptchaContainer SESSION_CONTAINER = new SessionCaptchaContainer();
}
