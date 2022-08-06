package com.my.web.locale.impl;

import com.my.web.filter.LocaleFilter;
import com.my.web.locale.LocaleContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class SessionLocaleContainer implements LocaleContainer {
    @Override
    public Locale getLocale(HttpServletRequest request) {
        return (Locale) request.getSession().getAttribute(LocaleFilter.LANGUAGE);
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        request.getSession().setAttribute(LocaleFilter.LANGUAGE, locale);
    }
}
