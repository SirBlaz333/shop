package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.entity.Captcha;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class DisplayCaptchaCommand implements Command {
    private final static String IMAGE_FORMAT = "jpeg";
    public static final String CAPTCHA_IMAGE = "captchaImg";

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession();
        BufferedImage captchaImg = (BufferedImage) httpSession.getAttribute(CAPTCHA_IMAGE);
        httpSession.removeAttribute(CAPTCHA_IMAGE);
        writeImage(response, captchaImg);
    }

    private void writeImage(HttpServletResponse response, BufferedImage bufferedImage) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, IMAGE_FORMAT, outputStream);
    }
}
