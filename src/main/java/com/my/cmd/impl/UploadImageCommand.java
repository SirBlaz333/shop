package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.entity.UserRegFields;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UploadImageCommand implements Command {

    public static final String IMAGES_FILEPATH = "ImagesFilepath";
    private static final String UPLOAD_RESULT = "Image has been uploaded";
    public static final String CONTENT_DISPOSITION = "content-disposition";
    public static final String FILENAME_PATTERN = ".*filename=\"(.*)\"";
    public static final int FILENAME_GROUP = 1;

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        Part part = request.getPart(UserRegFields.AVATAR);
        String imagesFilepath = request.getServletContext().getInitParameter(IMAGES_FILEPATH);
        if(part != null){
            InputStream inputStream = part.getInputStream();
            String fileName = getSubmittedFileName(part);
            File file = new File(imagesFilepath + fileName);
            FileUtils.copyInputStreamToFile(inputStream, file);
            request.getSession().setAttribute(UserRegFields.AVATAR_FILENAME, file.getName());
            response.getOutputStream().write(UPLOAD_RESULT.getBytes());
        }
    }

    private String getSubmittedFileName(Part part){
        String contentDisposition = part.getHeader(CONTENT_DISPOSITION);
        Pattern pattern = Pattern.compile(FILENAME_PATTERN);
        Matcher matcher = pattern.matcher(contentDisposition);
        if(matcher.find()){
            return matcher.group(FILENAME_GROUP);
        }
        return null;
    }

}
