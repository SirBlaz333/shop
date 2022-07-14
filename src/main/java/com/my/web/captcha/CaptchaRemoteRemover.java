package com.my.web.captcha;

import com.my.entity.Captcha;

import java.util.Map;

public class CaptchaRemoteRemover implements Runnable {

    private final Map<String, Captcha> captchaMap;
    private final long timeout;
    private final String key;

    public CaptchaRemoteRemover(Map<String, Captcha> captchaMap, long timeout, String key){
        this.captchaMap = captchaMap;
        this.timeout = timeout;
        this.key = key;
    }

    @Override
    public void run() {
        try{
            Thread.sleep(timeout);
            captchaMap.remove(key);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
