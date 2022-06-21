package com.my.web.command;

import com.my.web.captcha.Captcha;
import com.my.web.captcha.CaptchaTimeout;
import com.my.web.captcha.CaptchaContainer;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import static com.my.web.ContextListener.CAPTCHA_CONTAINER;
import static com.my.web.ContextListener.TIMEOUT;

public class CaptchaCommand implements Captcha, WebCommand {
    private final static long RANDOM_SEED = 111_111;
    private final static int RANDOM_ORIGIN = 100_000;
    private final static int RANDOM_BOUND = 999_999;
    private final static int CAPTCHA_HEIGHT = 50;
    private final static int CAPTCHA_WIDTH = 150;
    private final static String IMAGE_FORMAT = "jpeg";
    private final static Font FONT =  new Font("Arial", Font.BOLD, 32);
    private final Random random;

    public CaptchaCommand(){
        random = new Random(RANDOM_SEED);
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext servletContext = request.getServletContext();
        CaptchaContainer container = (CaptchaContainer) servletContext.getAttribute(CAPTCHA_CONTAINER);
        long timeout = (long) request.getServletContext().getAttribute(TIMEOUT);
        String captcha = generateCaptcha();
        container.put(request, response, captcha);
        BufferedImage bufferedImage = drawCaptcha(captcha);
        writeImage(response, bufferedImage);
        startCaptchaTimeout(container ,request.getSession(), captcha, timeout);
    }

    private String generateCaptcha(){
        int number = random.nextInt(RANDOM_BOUND - RANDOM_ORIGIN) + RANDOM_ORIGIN;
        return Integer.toString(number);
    }

    private BufferedImage drawCaptcha(String captcha){
        BufferedImage bufferedImage = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.OPAQUE);
        Graphics graphics = bufferedImage.createGraphics();
        graphics.setFont(FONT);
        graphics.setColor(Color.gray);
        graphics.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        graphics.setColor(Color.white);
        drawTextInCenter(captcha, graphics);
        return bufferedImage;
    }

    private void drawTextInCenter(String text, Graphics graphics){
        TextLayout textLayout = new TextLayout(text, graphics.getFont(), graphics.getFontMetrics().getFontRenderContext());
        double textHeight = textLayout.getBounds().getHeight();
        double textWidth = textLayout.getBounds().getWidth();
        int textX = CAPTCHA_WIDTH / 2 - (int) textWidth / 2;
        int textY = CAPTCHA_HEIGHT / 2 + (int) textHeight / 2;
        graphics.drawString(text, textX, textY);
    }

    private void writeImage(HttpServletResponse response, BufferedImage bufferedImage) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, IMAGE_FORMAT, outputStream);
    }

    private void startCaptchaTimeout(CaptchaContainer container ,HttpSession session, String captcha, long timeout){
        CaptchaTimeout captchaTimeout = new CaptchaTimeout(container, session, captcha, timeout);
        Thread thread = new Thread(captchaTimeout);
        thread.start();
    }
}
