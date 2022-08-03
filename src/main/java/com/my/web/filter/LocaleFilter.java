package com.my.web.filter;

import com.my.web.locale.LocaleContainer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@WebFilter(filterName = "LocaleFilter",
        urlPatterns = "/*")
public class LocaleFilter implements Filter {
    public static final String LANGUAGE = "lang";
    private Map<String, Locale> locales;
    private LocaleContainer localeContainer;
    private Locale defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        locales = new HashMap<>();
        locales.put("", new Locale("un"));
        // TODO: 02.08.2022 get locales and default locales from deployment descriptor
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String language = request.getParameter(LANGUAGE);
        Locale locale = locales.get(language);
        if (locale != null) {
            localeContainer.setLocale(request, response, locale);
            filterChain.doFilter(servletRequest, servletResponse);
        }
        locale = localeContainer.getLocale(request, response);
        if (locale != null) {
            localeContainer.setLocale(request, response, locale);
            filterChain.doFilter(servletRequest, servletResponse);
        }
        Enumeration<Locale> localeEnumeration = request.getLocales();
        while (localeEnumeration.hasMoreElements()) {
            Locale userLocale = localeEnumeration.nextElement();
            if (locales.containsValue(userLocale)) {
                locale = userLocale;
            }
        }
        if (locale == null) {
            locale = defaultLocale;
        }
        localeContainer.setLocale(request, response, locale);
        filterChain.doFilter(servletRequest, servletResponse);
        // TODO: 02.08.2022 getLocale and getLocales must return new locale
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
