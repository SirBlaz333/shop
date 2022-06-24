package com.my.web.captcha.container.impl;

public enum CaptchaContainerStrategy {
    COOKIE(new CookieCaptchaContainer()),
    SESSION(new SessionCaptchaContainer());
    private final com.my.web.captcha.container.CaptchaContainer container;
    CaptchaContainerStrategy(com.my.web.captcha.container.CaptchaContainer container) {
        this.container = container;
    }

    public com.my.web.captcha.container.CaptchaContainer getContainer() {
        return container;
    }
}
