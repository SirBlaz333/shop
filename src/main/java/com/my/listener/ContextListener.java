package com.my.listener;

import com.my.captcha.container.CaptchaContainer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.reflect.InvocationTargetException;

import static com.my.captcha.Captcha.CAPTCHA_CONTAINER;
import static com.my.captcha.Captcha.TIMEOUT;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        try{
            initCaptchaContainer(servletContext);
            initTimeout(servletContext);
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void initCaptchaContainer(ServletContext servletContext) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String className = servletContext.getInitParameter(CAPTCHA_CONTAINER);
        Class<?> containerClass = Class.forName(className);
        CaptchaContainer container = (CaptchaContainer) containerClass.getDeclaredConstructor().newInstance();
        servletContext.setAttribute(CAPTCHA_CONTAINER, container);
    }

    private void initTimeout(ServletContext servletContext){
        String timeout = servletContext.getInitParameter(TIMEOUT);
        servletContext.setAttribute(TIMEOUT, Long.parseLong(timeout));
    }
}
