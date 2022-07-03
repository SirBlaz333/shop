package com.my.web.captcha.container.impl;

import com.my.entity.Captcha;
import com.my.web.captcha.CaptchaRemoteRemover;
import com.my.web.captcha.container.CaptchaContainer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CaptchaContainerImpl implements CaptchaContainer {
    private final long timeout;
    private final Map<String, Captcha> captchaMap;

    public CaptchaContainerImpl(long timeout){
        this.timeout = timeout;
        captchaMap = new ConcurrentHashMap<>();
    }

    @Override
    public void put(String captchaKey, Captcha captcha) {
        captchaMap.put(captchaKey, captcha);
    }

    @Override
    public Captcha get(String captchaKey) {
        return captchaMap.get(captchaKey);
    }

    @Override
    public void remoteRemove(String captchaKey) {
        CaptchaRemoteRemover remoteRemover = new CaptchaRemoteRemover(captchaMap, timeout, captchaKey);
        Thread thread = new Thread(remoteRemover);
        thread.start();
    }
}
