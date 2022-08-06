package com.my.entity.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Locale;

public class LocaleRequestWrapper extends HttpServletRequestWrapper {
    private Locale locale;

    public LocaleRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return new LocaleEnumeration(locale);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    private static class LocaleEnumeration implements Enumeration<Locale> {
        private final Locale locale;
        private boolean hasMoreElements;

        public LocaleEnumeration(Locale locale) {
            this.locale = locale;
            hasMoreElements = true;
        }

        @Override
        public boolean hasMoreElements() {
            return hasMoreElements;
        }

        @Override
        public Locale nextElement() {
            hasMoreElements = false;
            return locale;
        }
    }
}
