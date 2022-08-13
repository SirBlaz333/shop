package com.my.web.listener;

import com.my.entity.security.PageAccessibility;
import com.my.service.security.SecurityService;
import com.my.service.security.impl.SecurityServiceImpl;
import com.my.xml.exception.XMLParsingException;
import com.my.xml.security.SecurityXMLParser;
import com.my.xml.security.impl.SecurityDOMParser;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class RestrictionsListener implements ServletContextListener {

    public static final String SECURITY_SERVICE = "SecurityService";
    private static final String RESTRICTIONS_FILEPATH = "RestrictionsFilepath";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String filepath = servletContext.getInitParameter(RESTRICTIONS_FILEPATH);
        PageAccessibility pageAccessibility = getPageAccessibility(filepath);
        SecurityService securityService = new SecurityServiceImpl(pageAccessibility);
        servletContext.setAttribute(SECURITY_SERVICE, securityService);
    }

    private PageAccessibility getPageAccessibility(String filepath) {
        try {
            SecurityXMLParser securityXMLParser = new SecurityDOMParser();
            return securityXMLParser.parse(filepath);
        } catch (XMLParsingException e) {
            throw new RuntimeException(e);
        }
    }
}
