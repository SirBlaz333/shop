package com.my.cmd.impl.util;

import com.my.entity.Captcha;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaUtility {
    private final static long RANDOM_SEED = 111_111;
    private final static int RANDOM_ORIGIN = 100_000;
    private final static int RANDOM_BOUND = 999_999;
    private final static int CAPTCHA_HEIGHT = 50;
    private final static int CAPTCHA_WIDTH = 150;
    private final static Font FONT = new Font("Arial", Font.BOLD, 32);
    private final Random random;

    public CaptchaUtility() {
        random = new Random(RANDOM_SEED);
    }

    public Captcha createCaptcha() {
        int number = random.nextInt(RANDOM_BOUND - RANDOM_ORIGIN) + RANDOM_ORIGIN;
        String captcha = Integer.toString(number);
        BufferedImage image = drawCaptcha(captcha);
        return new Captcha(captcha, image);
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
