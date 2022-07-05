package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import static com.my.cmd.impl.ShowLoginPageCommand.IMAGE_FORMAT;
import static com.my.entity.UserRegFields.AVATAR;

public class DisplayAvatarCommand implements Command {
    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        BufferedImage bufferedImage = (BufferedImage) request.getSession().getAttribute(AVATAR);
        OutputStream outputStream = response.getOutputStream();
        if(bufferedImage != null){
            ImageIO.write(bufferedImage, IMAGE_FORMAT, outputStream);
        }
    }
}
