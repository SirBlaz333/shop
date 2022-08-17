package com.my.cmd.impl;

import com.my.cmd.impl.util.RedirectionUtility;
import com.my.cmd.Method;
import com.my.entity.Captcha;
import com.my.service.ServiceException;
import com.my.service.user.UserService;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.my.cmd.impl.util.LoginUtility.*;
import static com.my.entity.UserRegFields.CAPTCHA;
import static com.my.entity.UserRegFields.EMAIL;
import static com.my.service.user.impl.UserServiceImpl.USER_ALREADY_EXISTS;
import static com.my.web.page.Pages.MAIN;
import static com.my.web.page.Pages.REGISTRATION;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationCommandTest {
    @Mock
    private CaptchaContainerStrategy container;
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
    @Mock
    private ServletContext servletContext;
    @Mock
    private RedirectionUtility redirectionUtility;
    @Mock
    private UserService userService;
    private RegistrationCommand registrationCommand;

    @Before
    public void setUp() {
        registrationCommand = new RegistrationCommand(container, userService, redirectionUtility);
        when(captcha.getText()).thenReturn("123");
        when(request.getSession()).thenReturn(httpSession);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(DisplayAvatarCommand.IMAGES_FILEPATH)).thenReturn("/images/");
    }

    @Test
    public void successRegistrationTest() throws ServletException, IOException {
        when(container.get(request)).thenReturn(captcha);
        when(request.getParameter(CAPTCHA)).thenReturn("123");
        when(request.getParameter(EMAIL)).thenReturn("123@gmail.com");
        when(redirectionUtility.getRedirectUrl(eq(request), any())).thenReturn(MAIN);

        registrationCommand.doCommand(request, response, Method.GET);

        verify(response).sendRedirect(MAIN);
    }

    @Test
    public void wrongCaptchaTest() throws ServletException, IOException {
        when(container.get(request)).thenReturn(captcha);
        when(request.getParameter(CAPTCHA)).thenReturn("321");

        registrationCommand.doCommand(request, response, Method.GET);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectionUtility).showError(eq(request), eq(response), eq(REGISTRATION), captor.capture());
        assertEquals(WRONG_CAPTCHA_MESSAGE, captor.getValue());
    }

    @Test
    public void captchaTimeoutTest() throws ServletException, IOException {
        when(container.get(request)).thenReturn(null);

        registrationCommand.doCommand(request, response, Method.GET);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectionUtility).showError(eq(request), eq(response), eq(REGISTRATION), captor.capture());
        assertEquals(TIMEOUT_MESSAGE, captor.getValue());
    }

    @Test
    public void userExistsErrorTest() throws ServletException, IOException, ServiceException {
        when(container.get(request)).thenReturn(captcha);
        when(request.getParameter(CAPTCHA)).thenReturn("123");
        when(request.getParameter(EMAIL)).thenReturn("valera12@gmail.com");
        when(userService.add(any(), any())).thenThrow(new ServiceException(USER_ALREADY_EXISTS));

        registrationCommand.doCommand(request, response, Method.GET);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectionUtility).showError(eq(request), eq(response), eq(REGISTRATION), captor.capture());
        assertEquals(USER_ALREADY_EXISTS, captor.getValue());
    }
}