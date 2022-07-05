package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.entity.User;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.my.cmd.impl.ShowLoginPageCommand.IMAGE_FORMAT;

public class DisplayAvatarCommand implements Command {
    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        OutputStream outputStream = response.getOutputStream();
        if(user.getImage() != null){
            ImageIO.write(user.getImage(), IMAGE_FORMAT, outputStream);
        }
    }
}
