package com.my.cmd.impl;

import com.my.dao.user.UserDAOMap;
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

import java.io.IOException;

import static com.my.cmd.impl.RegistrationCommand.*;
import static com.my.cmd.impl.util.RegistrationUtility.WRONG_CAPTCHA_MESSAGE;
import static com.my.entity.UserRegFields.CAPTCHA;
import static com.my.entity.UserRegFields.EMAIL;
import static com.my.service.user.UserServiceImpl.USER_ALREADY_EXISTS;
import static com.my.web.captcha.container.CaptchaContainer.TIMEOUT_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationCommandTest {
    private static final String CAPTCHA_VALUE = "123456";
    private static final String WRONG_CAPTCHA_VALUE = "12312312";
    @Mock
    private CaptchaContainer container;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    @Test
    public void successRegistrationTest() throws ServletException, IOException, CaptchaException {
        when(container.get(request)).thenReturn(CAPTCHA_VALUE);
        when(request.getParameter(CAPTCHA)).thenReturn(CAPTCHA_VALUE);
        when(request.getParameter(EMAIL)).thenReturn("123@gmail.com");

        RegistrationCommand registrationCommand = new RegistrationCommand(container, new UserServiceImpl(new UserDAOMap()));
        registrationCommand.doCommand(request, response);

        verify(response).sendRedirect(MAIN_PAGE);
    }

    @Test
    public void wrongCaptchaTest() throws ServletException, IOException, CaptchaException {
        when(container.get(request)).thenReturn(CAPTCHA_VALUE);
        when(container.get(request)).thenReturn(WRONG_CAPTCHA_VALUE);
        when(request.getParameter(CAPTCHA)).thenReturn(CAPTCHA_VALUE);
        when(request.getRequestDispatcher(REGISTRATION)).thenReturn(requestDispatcher);

        RegistrationCommand registrationCommand = new RegistrationCommand(container, new UserServiceImpl(new UserDAOMap()));
        registrationCommand.doCommand(request, response);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(eq(ERROR_MESSAGE), captor.capture());
        verify(requestDispatcher).forward(request, response);
        assertEquals(WRONG_CAPTCHA_MESSAGE, captor.getValue());
    }

    @Test
    public void captchaTimeoutTest() throws ServletException, IOException, CaptchaException {
        when(container.get(request)).thenThrow(new CaptchaException(TIMEOUT_MESSAGE));
        when(request.getRequestDispatcher(REGISTRATION)).thenReturn(requestDispatcher);

        RegistrationCommand registrationCommand = new RegistrationCommand(container, new UserServiceImpl(new UserDAOMap()));
        registrationCommand.doCommand(request, response);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(eq(ERROR_MESSAGE), captor.capture());
        verify(requestDispatcher).forward(request, response);
        assertEquals(TIMEOUT_MESSAGE, captor.getValue());
    }

    @Test
    public void userExistsErrorTest() throws ServletException, IOException, CaptchaException, ServiceException {
        when(container.get(request)).thenReturn(CAPTCHA_VALUE);
        when(request.getParameter(CAPTCHA)).thenReturn(CAPTCHA_VALUE);
        when(request.getRequestDispatcher(REGISTRATION)).thenReturn(requestDispatcher);
        UserService userService = mock(UserService.class);
        when(userService.add(any())).thenThrow(new ServiceException(USER_ALREADY_EXISTS));

        RegistrationCommand registrationCommand = new RegistrationCommand(container, userService);
        registrationCommand.doCommand(request, response);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(eq(ERROR_MESSAGE), captor.capture());
        verify(requestDispatcher).forward(request, response);
        assertEquals(USER_ALREADY_EXISTS, captor.getValue());
    }
}