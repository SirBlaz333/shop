package com.my.web.locale.impl;

import com.my.web.filter.LocaleFilter;
import com.my.web.locale.LocaleContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class CookieLocaleContainer implements LocaleContainer {
    private final int maxAge;

    public CookieLocaleContainer(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public Locale getLocale(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LocaleFilter.LANGUAGE)) {
                cookie.setMaxAge(maxAge);
                return new Locale(cookie.getValue());
            }
        }
        return null;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        Cookie cookie = new Cookie(LocaleFilter.LANGUAGE, locale.getLanguage());
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
