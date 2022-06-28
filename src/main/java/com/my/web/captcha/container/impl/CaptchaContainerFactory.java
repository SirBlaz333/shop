package com.my.web.captcha.container.impl;

import com.my.web.captcha.container.CaptchaContainer;

public class CaptchaContainerFactory {
    public CaptchaContainer create(CaptchaContainerStrategy strategy){
        if(strategy == CaptchaContainerStrategy.COOKIE_CONTAINER){
            return new CookieCaptchaContainer();
        }
        if(strategy == CaptchaContainerStrategy.SESSION_CONTAINER){
            return new SessionCaptchaContainer();
        }
        if(strategy == CaptchaContainerStrategy.HIDDEN_FIELD_CONTAINER){
            return new HiddenFieldCaptchaContainer();
        }
        return null;
    }
}
