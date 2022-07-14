package com.my.web.captcha.container.strategy;

import com.my.web.captcha.container.strategy.impl.CookieCaptchaContainerStrategy;
import com.my.web.captcha.container.strategy.impl.HiddenFieldCaptchaContainerStrategy;
import com.my.web.captcha.container.strategy.impl.SessionCaptchaContainerStrategy;

public class CaptchaContainerFactory {
    public CaptchaContainerStrategy create(CaptchaContainerStrategies strategy, long timeout){
        if(strategy == CaptchaContainerStrategies.COOKIE_CONTAINER){
            return new CookieCaptchaContainerStrategy(timeout);
        }
        if(strategy == CaptchaContainerStrategies.SESSION_CONTAINER){
            return new SessionCaptchaContainerStrategy(timeout);
        }
        if(strategy == CaptchaContainerStrategies.HIDDEN_FIELD_CONTAINER){
            return new HiddenFieldCaptchaContainerStrategy(timeout);
        }
        return null;
    }
}
