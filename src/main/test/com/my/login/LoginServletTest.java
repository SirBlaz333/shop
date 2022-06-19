package com.my.login;

import com.my.captcha.Captcha;
import com.my.captcha.container.CaptchaContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.my.captcha.Captcha.CAPTCHA_CONTAINER;
import static com.my.login.LoginServlet.*;
import static com.my.user.UserField.EMAIL;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;
    @Mock
    private CaptchaContainer container;
    @Mock
    private RequestDispatcher requestDispatcher;

    private static final String CAPTCHA = "123456";

    @Before
    public void setUp(){
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(CAPTCHA_CONTAINER)).thenReturn(container);
    }

    @Test
    public void SuccessLoginTest() throws ServletException, IOException {

        when(request.getParameter(Captcha.CAPTCHA)).thenReturn(CAPTCHA);
        when(request.getParameter(EMAIL.toString().toLowerCase())).thenReturn("arasda@gmail.com");
        when(container.get(request)).thenReturn(CAPTCHA);

        doServlet();

        verify(response).sendRedirect(MAIN_PAGE);
    }

    @Test
    public void UserExistsErrorTest() throws ServletException, IOException {
        when(request.getParameter(Captcha.CAPTCHA)).thenReturn(CAPTCHA);
        when(request.getParameter(EMAIL.toString().toLowerCase())).thenReturn("valera12@gmail.com");
        when(request.getRequestDispatcher(REGISTRATION)).thenReturn(requestDispatcher);
        when(container.get(request)).thenReturn(CAPTCHA);

        doServlet();

        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(eq(ERROR_MESSAGE), message.capture());
        Assert.assertEquals(message.getValue(), USER_EXISTS_MESSAGE);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void CaptchaExpiredErrorTest() throws ServletException, IOException {
        when(request.getParameter(Captcha.CAPTCHA)).thenReturn(CAPTCHA);
        when(request.getParameter(EMAIL.toString().toLowerCase())).thenReturn("valera12@gmail.com");
        when(request.getRequestDispatcher(REGISTRATION)).thenReturn(requestDispatcher);
        when(container.get(request)).thenReturn(null);

        doServlet();

        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(eq(ERROR_MESSAGE), message.capture());
        Assert.assertEquals(message.getValue(), TIMEOUT_MESSAGE);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void WrongCaptchaErrorTest() throws ServletException, IOException {
        when(request.getParameter(Captcha.CAPTCHA)).thenReturn("123123");
        when(request.getParameter(EMAIL.toString().toLowerCase())).thenReturn("valera12@gmail.com");
        when(request.getRequestDispatcher(REGISTRATION)).thenReturn(requestDispatcher);
        when(container.get(request)).thenReturn(CAPTCHA);

        doServlet();

        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(eq(ERROR_MESSAGE), message.capture());
        Assert.assertEquals(message.getValue(), WRONG_CAPTCHA_MESSAGE);

        verify(requestDispatcher).forward(request, response);
    }

    private void doServlet() throws ServletException, IOException {
        LoginServlet loginServlet = new LoginServlet();
        loginServlet.init(servletConfig);
        loginServlet.doPost(request, response);
    }
}