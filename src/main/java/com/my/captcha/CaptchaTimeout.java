package com.my.captcha;

import com.my.captcha.container.CaptchaContainer;

import javax.servlet.http.HttpSession;

public class CaptchaTimeout implements Runnable{


    private final HttpSession httpSession;
    private final String captcha;
    private final CaptchaContainer container;
    private final long timeout;

    public CaptchaTimeout(CaptchaContainer captchaContainer, HttpSession httpSession, String captcha, long timeout){
        container = captchaContainer;
        this.httpSession = httpSession;
        this.captcha = captcha;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try {
            removeCaptcha();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeCaptcha() throws InterruptedException {
        Thread.sleep(timeout);
        container.remove(httpSession, captcha);
    }
}
