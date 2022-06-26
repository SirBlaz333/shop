package com.my.web.captcha;

import com.my.web.captcha.container.CaptchaContainer;

import javax.servlet.http.HttpServletRequest;

public class CaptchaTimeout implements Runnable{
    private final HttpServletRequest request;
    private final String captcha;
    private final CaptchaContainer container;
    private final long timeout;

    public CaptchaTimeout(CaptchaContainer captchaContainer, HttpServletRequest request, String captcha, long timeout){
        container = captchaContainer;
        this.request = request;
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
        container.remove(request, captcha);
    }
}
