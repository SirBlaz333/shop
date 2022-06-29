package com.my.cmd.impl;

import com.my.dao.user.impl.UserDAOMap;
import com.my.entity.Captcha;
import com.my.service.ServiceException;
import com.my.service.user.UserService;
import com.my.service.user.UserServiceImpl;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.web.captcha.exception.CaptchaException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.my.cmd.impl.RegistrationCommand.*;
import static com.my.cmd.impl.ShowRegistrationPageCommand.REGISTRATION;
import static com.my.cmd.impl.util.RegistrationUtility.WRONG_CAPTCHA_MESSAGE;
import static com.my.entity.UserRegFields.CAPTCHA;
import static com.my.entity.UserRegFields.EMAIL;
import static com.my.service.user.UserServiceImpl.USER_ALREADY_EXISTS;
import static com.my.web.captcha.container.CaptchaContainer.TIMEOUT_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationCommandTest {
    @Mock
    private CaptchaContainer container;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Captcha captcha;
    @Mock
    private HttpSession httpSession;
    private final int timeout = 10;
    private RegistrationCommand registrationCommand;
    @Before
    public void setUp(){
        UserService userService = new UserServiceImpl(new UserDAOMap());
        ShowRegistrationPageCommand showRegistrationPageCommand = new ShowRegistrationPageCommand(container);
        registrationCommand = new RegistrationCommand(container, userService, timeout, showRegistrationPageCommand);
        when(captcha.getText()).thenReturn("123");
        when(request.getRequestDispatcher(REGISTRATION)).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void successRegistrationTest() throws ServletException, IOException, CaptchaException {
        when(container.getWithTimeout(request, timeout)).thenReturn(captcha);
        when(request.getParameter(CAPTCHA)).thenReturn("123");
        when(request.getParameter(EMAIL)).thenReturn("123@gmail.com");

        registrationCommand.doCommand(request, response);

        verify(response).sendRedirect(MAIN_PAGE);
    }

    @Test
    public void wrongCaptchaTest() throws ServletException, IOException, CaptchaException {
        when(container.getWithTimeout(request, timeout)).thenReturn(captcha);
        when(request.getParameter(CAPTCHA)).thenReturn("321");

        registrationCommand.doCommand(request, response);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(eq(ERROR_MESSAGE), captor.capture());
        verify(requestDispatcher).forward(request, response);
        assertEquals(WRONG_CAPTCHA_MESSAGE, captor.getValue());
    }

    @Test
    public void captchaTimeoutTest() throws ServletException, IOException, CaptchaException {
        when(container.getWithTimeout(request, timeout)).thenThrow(new CaptchaException(TIMEOUT_MESSAGE));

        registrationCommand.doCommand(request, response);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(eq(ERROR_MESSAGE), captor.capture());
        verify(requestDispatcher).forward(request, response);
        assertEquals(TIMEOUT_MESSAGE, captor.getValue());
    }

    @Test
    public void userExistsErrorTest() throws ServletException, IOException, CaptchaException, ServiceException {
        when(container.getWithTimeout(request, timeout)).thenReturn(captcha);
        when(request.getParameter(CAPTCHA)).thenReturn("123");
        when(request.getParameter(EMAIL)).thenReturn("valera12@gmail.com");

        registrationCommand.doCommand(request, response);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(eq(ERROR_MESSAGE), captor.capture());
        verify(requestDispatcher).forward(request, response);
        assertEquals(USER_ALREADY_EXISTS, captor.getValue());
    }
}