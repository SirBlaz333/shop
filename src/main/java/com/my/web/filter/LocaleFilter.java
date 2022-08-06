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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class LocaleFilter implements Filter {
    public static final String LANGUAGE = "lang";
    private List<Locale> locales;
    private LocaleContainer localeContainer;
    private Locale defaultLocale;

    public LocaleFilter() {
    }

    LocaleFilter(LocaleContainer localeContainer, List<Locale> locales) {
        this.localeContainer = localeContainer;
        this.locales = locales;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        localeContainer = getLocaleContainer(filterConfig);
        String defaultLanguage = filterConfig.getInitParameter("DefaultLocale");
        defaultLocale = new Locale(defaultLanguage);
        locales = getLocales();
        locales.add(defaultLocale);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Locale locale = defaultLocale;
        locale = getLocaleFromLocalesOrDefault(request, locale);
        locale = getLocaleFromContainerOrDefault(request, locale);
        locale = getLocaleFromRequestOrDefault(request, locale);
        localeContainer.setLocale(request, response, locale);
        LocaleRequestWrapper requestWrapper = new LocaleRequestWrapper(request);
        requestWrapper.setLocale(locale);
        chain.doFilter(requestWrapper, response);
    }

    private Locale getLocaleFromLocalesOrDefault(HttpServletRequest request, Locale defaultLocale) {
        Enumeration<Locale> localeEnumeration = request.getLocales();
        while (localeEnumeration.hasMoreElements()) {
            Locale userLocale = localeEnumeration.nextElement();
            if (locales.contains(userLocale)) {
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
        if (language == null) {
            return defaultLocale;
        }
        Locale locale = new Locale(language);
        if (locales.contains(locale)) {
            return locale;
        }
        return defaultLocale;
    }

    private LocaleContainer getLocaleContainer(FilterConfig filterConfig) {
        if (localeContainer != null) {
            return localeContainer;
        }
        try {
            String className = filterConfig.getInitParameter("LocaleContainer");
            Class<?> localeContainerClass = Class.forName(className);
            return (LocaleContainer) localeContainerClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Locale> getLocales() {
        if (locales != null) {
            return locales;
        }
        // TODO: 06.08.2022 get locales from deployment descriptor
        return new ArrayList<>();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
