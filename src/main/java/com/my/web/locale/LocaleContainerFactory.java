package com.my.web.locale;

import com.my.web.locale.exception.LocaleContainerFactoryException;

import javax.servlet.FilterConfig;

public interface LocaleContainerFactory {
    String LOCALE_CONTAINER = "LocaleContainer";
    LocaleContainer getLocaleContainer(FilterConfig filterConfig) throws LocaleContainerFactoryException;
}
