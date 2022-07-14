package com.my.web.captcha.container;

import com.my.entity.Captcha;

public interface CaptchaContainer {
    void put(String captchaKey, Captcha captcha);
    Captcha get(String captchaKey);
    void remoteRemove(String captchaKey);
}
