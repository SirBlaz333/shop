package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.entity.User;
import com.my.entity.UserRegFields;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class DisplayAvatarCommand implements Command {
    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(UserRegFields.USER);
        OutputStream outputStream = response.getOutputStream();
        if(user.getImage() != null){
            ImageIO.write(user.getImage(), ShowLoginPageCommand.IMAGE_FORMAT, outputStream);
        }
    }
}
