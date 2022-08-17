package com.my.web.locale.impl;

import com.my.web.locale.LocaleContainer;
import com.my.web.locale.LocaleContainerFactory;
import com.my.web.locale.exception.LocaleContainerFactoryException;

import javax.servlet.FilterConfig;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class LocaleContainerFactoryImpl implements LocaleContainerFactory {
    private static final String INITIALIZATION_ERROR = "Cannot init locale container";
    private static final String NO_SUCH_CONTAINER = "There is no such LocaleContainer implementation with such name: ";
    private static final String INIT_CONSTRUCTOR_ERROR = "Cannot init constructor of class for: ";

    @Override
    public LocaleContainer getLocaleContainer(FilterConfig filterConfig) throws LocaleContainerFactoryException {
        try {
            return createLocaleContainer(filterConfig);
        } catch (NumberFormatException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException e) {
            throw new LocaleContainerFactoryException(INITIALIZATION_ERROR);
        }
    }

    private LocaleContainer createLocaleContainer(FilterConfig filterConfig) throws InvocationTargetException, InstantiationException, IllegalAccessException, LocaleContainerFactoryException {
        String classname = filterConfig.getInitParameter(LOCALE_CONTAINER);
        if (classname.equals(SessionLocaleContainer.class.getName())) {
            return (LocaleContainer) getConstructor(classname).newInstance();
        }
        if (classname.equals(CookieLocaleContainer.class.getName())) {
            int maxAge = Integer.parseInt(filterConfig.getInitParameter("CookieMaxAge"));
            return (LocaleContainer) getConstructor(classname, int.class).newInstance(maxAge);
        }
        throw new LocaleContainerFactoryException(NO_SUCH_CONTAINER + classname);
    }

    private Constructor<?> getConstructor(String className, Class<?>... constructorArgs) throws LocaleContainerFactoryException {
        try {
            Class<?> localeContainerClass = Class.forName(className);
            return localeContainerClass.getConstructor(constructorArgs);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new LocaleContainerFactoryException(INIT_CONSTRUCTOR_ERROR + className);
        }
    }

}
