package com.my.web.filter;

import com.my.entity.request.LocaleRequestWrapper;
import com.my.web.locale.LocaleContainer;
import com.my.web.filter.parser.ParamParser;

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
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LocaleFilter implements Filter {
    public static final String LANGUAGE = "lang";
    public static final String LOCALES = "Locales";
    public static final String LOCALE_CONTAINER = "LocaleContainer";
    public static final String DEFAULT_LOCALE = "DefaultLocale";
    public static final String RESOURCE_BUNDLE_BASE_NAME = "locale";
    private ParamParser paramParser;
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
        paramParser = new ParamParser();
        localeContainer = getLocaleContainer(filterConfig);
        defaultLocale = getDefaultLocale(filterConfig);
        locales = getLocales(filterConfig);
        validateResourceBundlesExistence(locales);
    }

    private Locale getDefaultLocale(FilterConfig filterConfig){
        String defaultLanguage = filterConfig.getInitParameter(DEFAULT_LOCALE);
        return new Locale(defaultLanguage);
    }

    private LocaleContainer getLocaleContainer(FilterConfig filterConfig) throws ServletException {
        if (localeContainer != null) {
            return localeContainer;
        }
        try {
            String className = filterConfig.getInitParameter(LOCALE_CONTAINER);
            Class<?> localeContainerClass = Class.forName(className);
            return (LocaleContainer) localeContainerClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new ServletException(e);
        }
    }

    private List<Locale> getLocales(FilterConfig filterConfig) {
        if (locales != null) {
            return locales;
        }
        String localesParam = filterConfig.getInitParameter(LOCALES);
        return paramParser.getParams(localesParam)
                .stream()
                .map(Locale::new)
                .collect(Collectors.toList());
    }

    private void validateResourceBundlesExistence(List<Locale> locales) {
        for (Locale locale : locales) {
            ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME, locale);
        }
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
        LocaleRequestWrapper requestWrapper = new LocaleRequestWrapper(request, locale, locales);
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
}
