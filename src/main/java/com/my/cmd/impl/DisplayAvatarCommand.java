package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.entity.User;
import com.my.entity.UserRegFields;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DisplayAvatarCommand implements Command {
    public static final String IMAGES_FILEPATH = "ImagesFilepath";
    public static final String CANNOT_DISPLAY_AVATAR = "Cannot display avatar";
    private final Logger logger;

    public DisplayAvatarCommand(){
        this.logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) {
        try {
            displayImage(request, response);
        } catch (IOException e){
            logger.log(Level.SEVERE, CANNOT_DISPLAY_AVATAR);
        }
    }

    private void displayImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute(UserRegFields.USER);
        OutputStream outputStream = response.getOutputStream();
        String imagesFilepath = request.getServletContext().getInitParameter(IMAGES_FILEPATH);
        File avatar = new File(imagesFilepath + user.getId());
        if (avatar.exists()) {
            BufferedImage bufferedImage = ImageIO.read(avatar);
            ImageIO.write(bufferedImage, ShowLoginPageCommand.IMAGE_FORMAT, outputStream);
        }
    }
}
