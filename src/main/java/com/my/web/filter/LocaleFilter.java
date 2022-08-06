package com.my.web.filter;

import com.my.entity.request.LocaleRequestWrapper;
import com.my.web.locale.LocaleContainer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocaleFilter implements Filter {
    public static final String LANGUAGE = "lang";
    private Map<String, Locale> locales;
    private LocaleContainer localeContainer;
    private Locale defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        localeContainer = getLocaleContainer(filterConfig);
        String defaultLanguage = filterConfig.getInitParameter("DefaultLocale");
        defaultLocale = new Locale(defaultLanguage);
        locales = new HashMap<>();
        locales.put(defaultLanguage, defaultLocale);
        // TODO: 02.08.2022 get locales from deployment descriptor
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        LocaleRequestWrapper request = new LocaleRequestWrapper((HttpServletRequest) servletRequest);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Locale locale = defaultLocale;
        locale = getLocaleFromLocalesOrDefault(request, locale);
        locale = getLocaleFromContainerOrDefault(request, locale);
        locale = getLocaleFromRequestOrDefault(request, locale);
        localeContainer.setLocale(request, response, locale);
        request.setLocale(locale);
        chain.doFilter(request, response);
    }

    private Locale getLocaleFromLocalesOrDefault(HttpServletRequest request, Locale defaultLocale) {
        Enumeration<Locale> localeEnumeration = request.getLocales();
        while (localeEnumeration.hasMoreElements()) {
            Locale userLocale = localeEnumeration.nextElement();
            if (locales.containsValue(userLocale)) {
                return userLocale;
            }
        }
        return defaultLocale;
    }

    private Locale getLocaleFromContainerOrDefault(HttpServletRequest request, Locale defaultLocale) {
        Locale locale = localeContainer.getLocale(request);
        if (locale != null) {
            return locale;
        }
        return defaultLocale;
    }

    private Locale getLocaleFromRequestOrDefault(HttpServletRequest request, Locale defaultLocale) {
        String language = request.getParameter(LANGUAGE);
        Locale locale = locales.get(language);
        if (locale != null) {
            return locale;
        }
        return defaultLocale;
    }

    private LocaleContainer getLocaleContainer(FilterConfig filterConfig) {
        try {
            String className = filterConfig.getInitParameter("LocaleContainer");
            Class<?> localeContainerClass = Class.forName(className);
            return (LocaleContainer) localeContainerClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
