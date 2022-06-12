package com.my.captcha;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@WebServlet("/captcha-servlet")
public class CaptchaServlet extends HttpServlet {

    public static final String CAPTCHA = "captcha";
    private final static long RANDOM_SEED = 111_111;
    private final static int RANDOM_ORIGIN = 100_000;
    private final static int RANDOM_BOUND = 999_999;
    private final static int CAPTCHA_HEIGHT = 50;
    private final static int CAPTCHA_WIDTH = 150;
    private final static String IMAGE_FORMAT = "jpeg";
    private final static Font FONT =  new Font("Arial", Font.BOLD, 32);
    private final Random random;

    public CaptchaServlet(){
        random = new Random(RANDOM_SEED);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String captcha = generateCaptcha();
        BufferedImage bufferedImage = drawCaptcha(captcha);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(CAPTCHA, captcha);
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, IMAGE_FORMAT, outputStream);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    private String generateCaptcha(){
        int number = random.nextInt(RANDOM_ORIGIN, RANDOM_BOUND);
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
}
