package com.my.entity;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import java.awt.image.BufferedImage;

public class Captcha {
    private final String text;
    private final BufferedImage image;
    private final DateTime creationTime;

    public Captcha(String captcha, BufferedImage bufferedImage){
        this.text = captcha;
        image = bufferedImage;
        creationTime = DateTime.now();
    }

    public boolean isExpired(int timeout){
        DateTime currentTime = DateTime.now();
        DateTime expireTime = creationTime.plusSeconds(timeout);
        ReadableInstant currentInstant = currentTime.toInstant();
        return expireTime.compareTo(currentInstant) <= 0;
    }

    public String getText() {
        return text;
    }

    public BufferedImage getImage() {
        return image;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }
}
