package com.my.web.filter;

import com.my.cmd.impl.util.RedirectionUtility;
import com.my.entity.user.User;
import com.my.entity.user.UserRegFields;
import com.my.service.security.SecurityService;
import com.my.web.listener.RestrictionsListener;
import com.my.web.page.Pages;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter implements Filter {
    public static final String USER_IS_NOT_LOGGED_IN = "You are not logged in. Please log in and try again";
    public static final String ACCESS_RESTRICTION = "You don't have access to this page";
    private RedirectionUtility redirectionUtility;
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        redirectionUtility = new RedirectionUtility();
        ServletContext servletContext = filterConfig.getServletContext();
        securityService = (SecurityService) servletContext.getAttribute(RestrictionsListener.SECURITY_SERVICE);
    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(UserRegFields.USER);
        String url = request.getRequestURI();
        if(!securityService.pageIsRestricted(url)){
            filterChain.doFilter(request, response);
            return;
        }
        if (user == null) {
            request.setAttribute(RedirectionUtility.FROM, url);
            redirectionUtility.showError(request, response, Pages.LOGIN, USER_IS_NOT_LOGGED_IN);
            return;
        }
        if(!securityService.userHasAccess(user.getUserRole(), url)){
            redirectionUtility.showError(request, response, Pages.ERROR, ACCESS_RESTRICTION);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
