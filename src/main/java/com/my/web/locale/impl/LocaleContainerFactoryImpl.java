package com.my.web.locale.impl;

import com.my.web.locale.LocaleContainer;
import com.my.web.locale.LocaleContainerFactory;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class LocaleContainerFactoryImpl implements LocaleContainerFactory {
    @Override
    public LocaleContainer getLocaleContainer(FilterConfig filterConfig) throws ServletException {
        try {
            return createLocaleContainer(filterConfig);
        } catch (NumberFormatException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException e) {
            throw new ServletException(e);
        }
    }

    private LocaleContainer createLocaleContainer(FilterConfig filterConfig) throws ServletException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String classname = filterConfig.getInitParameter(LOCALE_CONTAINER);
        if (classname.equals(SessionLocaleContainer.class.getName())) {
            return (LocaleContainer) getConstructor(classname).newInstance();
        }
        if (classname.equals(CookieLocaleContainer.class.getName())) {
            int maxAge = Integer.parseInt(filterConfig.getInitParameter("CookieMaxAge"));
            return (LocaleContainer) getConstructor(classname, int.class).newInstance(maxAge);
        }
        return null;
    }

    private Constructor<?> getConstructor(String className, Class<?>... constructorArgs) throws ServletException {
        try {
            Class<?> localeContainerClass = Class.forName(className);
            return localeContainerClass.getConstructor(constructorArgs);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new ServletException(e);
        }
    }

}
