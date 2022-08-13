package com.my.web.locale;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public interface LocaleContainerFactory {
    String LOCALE_CONTAINER = "LocaleContainer";
    LocaleContainer getLocaleContainer(FilterConfig filterConfig) throws ServletException;
}
