package com.my.cmd.impl.util;

import com.my.cmd.Method;
import com.my.cmd.impl.ShowLoginPageCommand;
import com.my.cmd.impl.UploadImageCommand;
import com.my.entity.Captcha;
import com.my.entity.User;
import com.my.entity.UserBuilder;
import com.my.entity.UserRegFields;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;
import com.my.web.captcha.exception.CaptchaException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginUtility {
    public static final String MAIN_PAGE = "index.jsp";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String TIMEOUT_MESSAGE = "Captcha expired. Please try again";
    public static final String WRONG_CAPTCHA_MESSAGE = "You enter wrong number. Please try again";
    private final ShowLoginPageCommand showLoginPageCommand;
    private final CaptchaContainerStrategy container;

    public LoginUtility(ShowLoginPageCommand showLoginPageCommand, CaptchaContainerStrategy captchaContainer){
        this.showLoginPageCommand = showLoginPageCommand;
        container = captchaContainer;
    }

    public void checkCaptcha(HttpServletRequest request) throws CaptchaException {
        Captcha expectedCaptcha = container.get(request);
        String userCaptcha = request.getParameter(UserRegFields.CAPTCHA);
        checkCaptcha(expectedCaptcha, userCaptcha);
    }

    public User createUser(HttpServletRequest request){
        String email = request.getParameter(UserRegFields.EMAIL);
        String firstname = request.getParameter(UserRegFields.FIRSTNAME);
        String lastname = request.getParameter(UserRegFields.LASTNAME);
        String password = request.getParameter(UserRegFields.PASSWORD);
        String newsletterParameter = request.getParameter(UserRegFields.NEWSLETTER);
        boolean newsletter = (newsletterParameter != null);
        return new UserBuilder().
                withEmail(email).
                withFirstname(firstname).
                withLastname(lastname).
                withPassword(password).
                withNewsletter(newsletter).
                getUser();
    }

    public void attachAndRenameImageInFolder(HttpSession httpSession, User user) throws IOException {
        String filepath = (String) httpSession.getAttribute(UserRegFields.AVATAR_FILENAME);
        httpSession.removeAttribute(UserRegFields.AVATAR);
        String imageFilepath = httpSession.getServletContext().getInitParameter(UploadImageCommand.IMAGES_FILEPATH);
        File file = new File(imageFilepath + filepath);
        if(file.exists()){
            BufferedImage bufferedImage = ImageIO.read(file);
            user.setImage(bufferedImage);
            String newFilename = imageFilepath + user.getId();
            File newFile = new File(newFilename);
            file.renameTo(newFile);
        }
    }

    public void attachImage(HttpSession httpSession, User user) throws IOException {
        String imageFilepath = httpSession.getServletContext().getInitParameter(UploadImageCommand.IMAGES_FILEPATH);
        String fileName = imageFilepath + user.getId();
        File file = new File(fileName);
        if(file.exists()){
            user.setImage(ImageIO.read(file));
        }
    }

    private void checkCaptcha(Captcha expectedCaptcha, String actualCaptcha) throws CaptchaException {
        if(expectedCaptcha == null){
            throw new CaptchaException(TIMEOUT_MESSAGE);
        }
        if(!expectedCaptcha.getText().equals(actualCaptcha)){
            throw new CaptchaException(WRONG_CAPTCHA_MESSAGE);
        }
    }


    public void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        setAttributesForForward(request);
        showLoginPageCommand.doCommand(request, response, Method.POST);
    }

    private void setAttributesForForward(HttpServletRequest request){
        request.setAttribute(UserRegFields.EMAIL, request.getParameter(UserRegFields.EMAIL));
        request.setAttribute(UserRegFields.FIRSTNAME, request.getParameter(UserRegFields.FIRSTNAME));
        request.setAttribute(UserRegFields.LASTNAME, request.getParameter(UserRegFields.LASTNAME));
    }
}
