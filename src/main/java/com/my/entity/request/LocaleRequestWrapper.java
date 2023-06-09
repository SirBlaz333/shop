package com.my.entity.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class LocaleRequestWrapper extends HttpServletRequestWrapper {
    private final Locale locale;

    public LocaleRequestWrapper(HttpServletRequest request, Locale locale) {
        super(request);
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        List<Locale> locales = new ArrayList<>();
        locales.add(locale);
        return Collections.enumeration(locales);
    }
}
