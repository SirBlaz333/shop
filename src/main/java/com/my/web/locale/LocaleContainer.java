package com.my.web.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public interface LocaleContainer {
    Locale getLocale(HttpServletRequest request, HttpServletResponse response);
    void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale);
}
