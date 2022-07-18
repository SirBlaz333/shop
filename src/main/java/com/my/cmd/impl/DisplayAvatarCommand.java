package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.LoginUtility;
import com.my.entity.User;
import com.my.entity.UserRegFields;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class DisplayAvatarCommand implements Command {
    public static final String IMAGES_FILEPATH = "ImagesFilepath";
    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws IOException {
        User user = (User) request.getSession().getAttribute(UserRegFields.USER);
        OutputStream outputStream = response.getOutputStream();
        String imagesFilepath = request.getServletContext().getInitParameter(IMAGES_FILEPATH);
        File avatar = new File(imagesFilepath + user.getId());
        if(avatar.exists()){
            BufferedImage bufferedImage = ImageIO.read(avatar);
            ImageIO.write(bufferedImage, ShowLoginPageCommand.IMAGE_FORMAT, outputStream);
        }
    }
}
