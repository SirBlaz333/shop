package com.my.entity;

import java.awt.image.BufferedImage;

public class Captcha {
    private final String text;
    private final BufferedImage image;

    public Captcha(String captcha, BufferedImage bufferedImage){
        this.text = captcha;
        image = bufferedImage;
    }

    public String getText() {
        return text;
    }

    public BufferedImage getImage() {
        return image;
    }
}
