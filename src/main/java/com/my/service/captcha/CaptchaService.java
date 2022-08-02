package com.my.service.captcha;

import com.my.entity.Captcha;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaService {
    private final static long RANDOM_SEED = 111_111;
    private final static int CAPTCHA_ORIGIN = 100_000;
    private final static int CAPTCHA_BOUND = 999_999;
    private final static int KEY_ORIGIN = 100_000_000;
    private final static int KEY_BOUND = 999_999_999;
    private final static int CAPTCHA_HEIGHT = 50;
    private final static int CAPTCHA_WIDTH = 150;
    private final static Font FONT = new Font("Arial", Font.BOLD, 32);
    private final Random random;

    public CaptchaService() {
        this.random = new Random(RANDOM_SEED);
    }

    public Captcha createCaptcha() {
        int number = random.nextInt(CAPTCHA_BOUND - CAPTCHA_ORIGIN) + CAPTCHA_ORIGIN;
        String captcha = Integer.toString(number);
        BufferedImage image = drawCaptcha(captcha);
        return new Captcha(captcha, image);
    }

    public String createCaptchaKey(){
        int number = random.nextInt(KEY_BOUND- KEY_ORIGIN) + KEY_ORIGIN;
        return Integer.toString(number);
    }

    private BufferedImage drawCaptcha(String captcha) {
        BufferedImage bufferedImage = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.OPAQUE);
        Graphics graphics = bufferedImage.createGraphics();
        graphics.setFont(FONT);
        graphics.setColor(Color.gray);
        graphics.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        graphics.setColor(Color.white);
        drawTextInCenter(captcha, graphics);
        return bufferedImage;
    }

    private void drawTextInCenter(String text, Graphics graphics) {
        TextLayout textLayout = new TextLayout(text, graphics.getFont(), graphics.getFontMetrics().getFontRenderContext());
        double textHeight = textLayout.getBounds().getHeight();
        double textWidth = textLayout.getBounds().getWidth();
        int textX = CAPTCHA_WIDTH / 2 - (int) textWidth / 2;
        int textY = CAPTCHA_HEIGHT / 2 + (int) textHeight / 2;
        graphics.drawString(text, textX, textY);
    }

}
