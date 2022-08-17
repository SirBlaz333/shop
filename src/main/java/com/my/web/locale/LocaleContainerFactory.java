package com.my.web.locale;

import com.my.web.locale.exception.LocaleContainerFactoryException;

import javax.servlet.FilterConfig;

public interface LocaleContainerFactory {
    LocaleContainer getLocaleContainer(FilterConfig filterConfig) throws LocaleContainerFactoryException;
}
