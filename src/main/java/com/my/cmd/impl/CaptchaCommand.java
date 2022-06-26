package com.my.cmd.impl;

import com.my.web.captcha.CaptchaTimeout;
import com.my.cmd.impl.util.CaptchaUtility;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.cmd.Command;

import javax.imageio.ImageIO;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class CaptchaCommand implements Command {
    private final static String IMAGE_FORMAT = "jpeg";
    private final CaptchaUtility captchaUtility;
    private final CaptchaContainer container;
    private final long timeout;

    public CaptchaCommand(CaptchaContainer captchaContainer, long timeout) {
        captchaUtility = new CaptchaUtility();
        container = captchaContainer;
        this.timeout = timeout;
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String captcha = captchaUtility.generateCaptcha();
        container.put(request, response, captcha);
        BufferedImage bufferedImage = captchaUtility.drawCaptcha(captcha);
        writeImage(response, bufferedImage);
        startCaptchaTimeout(container, request, captcha, timeout);
    }

    private void writeImage(HttpServletResponse response, BufferedImage bufferedImage) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, IMAGE_FORMAT, outputStream);
    }

    private void startCaptchaTimeout(CaptchaContainer container, HttpServletRequest request, String captcha, long timeout) {
        CaptchaTimeout captchaTimeout = new CaptchaTimeout(container, request, captcha, timeout);
        Thread thread = new Thread(captchaTimeout);
        thread.start();
    }
}
