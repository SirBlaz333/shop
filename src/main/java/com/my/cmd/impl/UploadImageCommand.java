package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static com.my.entity.UserRegFields.AVATAR;

public class UploadImageCommand implements Command {

    private static final String UPLOAD_RESULT = "Image has been uploaded";

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        Part part = request.getPart(AVATAR);
        if(part != null){
            InputStream inputStream = part.getInputStream();
            BufferedImage avatar = ImageIO.read(inputStream);
            request.getSession().setAttribute(AVATAR, avatar);
            response.getOutputStream().write(UPLOAD_RESULT.getBytes());
        }
    }

}
