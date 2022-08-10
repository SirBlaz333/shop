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
    private final List<Locale> locales;

    public LocaleRequestWrapper(HttpServletRequest request, Locale locale, List<Locale> locales) {
        super(request);
        this.locale = locale;
        this.locales = new ArrayList<>(locales);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return Collections.enumeration(locales);
    }
}
