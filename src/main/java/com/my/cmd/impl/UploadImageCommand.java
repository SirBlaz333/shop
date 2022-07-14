package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
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

import static com.my.entity.UserRegFields.AVATAR;
import static com.my.entity.UserRegFields.AVATAR_FILENAME;

public class UploadImageCommand implements Command {

    private static final String UPLOAD_RESULT = "Image has been uploaded";
    public final static String IMAGES_FILEPATH = "images\\";
    public static final String CONTENT_DISPOSITION = "content-disposition";
    public static final String FILENAME_PATTERN = ".*filename=\"(.*)\"";
    public static final int FILENAME_GROUP = 1;

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        Part part = request.getPart(AVATAR);
        if(part != null){
            InputStream inputStream = part.getInputStream();
            String fileName = getSubmittedFileName(part);
            File file = new File(IMAGES_FILEPATH + fileName);
            FileUtils.copyInputStreamToFile(inputStream, file);
            request.getSession().setAttribute(AVATAR_FILENAME, file.getName());
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
