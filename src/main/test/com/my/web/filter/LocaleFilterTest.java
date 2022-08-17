package com.my.web.filter;

import com.my.web.locale.LocaleContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LocaleFilterTest {
    private LocaleFilter localeFilter;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private LocaleContainer localeContainer;
    @Mock
    private List<Locale> locales;

    @Before
    public void setUp() {
        locales = new ArrayList<>();
        locales.add(Locale.ENGLISH);
        locales.add(Locale.GERMAN);
        locales.add(Locale.FRENCH);
        when(request.getLocales()).thenReturn(Collections.enumeration(new ArrayList<>()));
        localeFilter = new LocaleFilter(localeContainer, locales, new Locale("en"));
    }

    @Test
    public void noLocaleTest() throws ServletException, IOException {
        localeFilter.doFilter(request, response, filterChain);
        testLocale(Locale.ENGLISH);
    }

    @Test
    public void onlyParamTest() throws ServletException, IOException {
        when(request.getParameter("lang")).thenReturn("en");
        localeFilter.doFilter(request, response, filterChain);
        testLocale(Locale.ENGLISH);
    }

    @Test
    public void onlyContainerTest() throws ServletException, IOException {
        when(localeContainer.getLocale(request)).thenReturn(Locale.ENGLISH);
        localeFilter.doFilter(request, response, filterChain);
        testLocale(Locale.ENGLISH);
    }

    @Test
    public void onlyLocalesTest() throws ServletException, IOException {
        when(request.getLocales()).thenReturn(createEnumeration());
        localeFilter.doFilter(request, response, filterChain);
        testLocale(Locale.ENGLISH);
    }

    @Test
    public void allParametersTest() throws ServletException, IOException {
        when(request.getParameter("lang")).thenReturn("de");
        when(localeContainer.getLocale(request)).thenReturn(Locale.FRENCH);
        List<Locale> locales = new ArrayList<>();
        locales.add(Locale.US);
        Enumeration<Locale> localeEnumeration = Collections.enumeration(locales);
        when(request.getLocales()).thenReturn(localeEnumeration);
        localeFilter.doFilter(request, response, filterChain);
        testLocale(Locale.GERMAN);
    }

    @Test
    public void allParametersButRequestedIsNotSupportedTest() throws ServletException, IOException {
        when(request.getParameter("lang")).thenReturn("ja");
        when(localeContainer.getLocale(request)).thenReturn(Locale.FRENCH);
        List<Locale> locales = new ArrayList<>();
        locales.add(Locale.US);
        Enumeration<Locale> localeEnumeration = Collections.enumeration(locales);
        when(request.getLocales()).thenReturn(localeEnumeration);
        localeFilter.doFilter(request, response, filterChain);
        testLocale(Locale.FRENCH);
    }

    @Test
    public void requestedAndLocalesButRequestedIsNotSupportedTest() throws ServletException, IOException {
        when(request.getParameter("lang")).thenReturn("ja");
        List<Locale> locales = new ArrayList<>();
        locales.add(Locale.ENGLISH);
        Enumeration<Locale> localeEnumeration = Collections.enumeration(locales);
        when(request.getLocales()).thenReturn(localeEnumeration);
        localeFilter.doFilter(request, response, filterChain);
        testLocale(Locale.ENGLISH);
    }

    @Test
    public void noSupportedLocales() throws ServletException, IOException {
        when(request.getParameter("lang")).thenReturn("ja");
        List<Locale> locales = new ArrayList<>();
        locales.add(Locale.JAPANESE);
        Enumeration<Locale> localeEnumeration = Collections.enumeration(locales);
        when(request.getLocales()).thenReturn(localeEnumeration);
        localeFilter.doFilter(request, response, filterChain);
        testLocale(Locale.ENGLISH);
    }

    @Test
    public void testLocales() throws ServletException, IOException {
        localeFilter.doFilter(request, response, filterChain);
        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        verify(filterChain).doFilter(requestCaptor.capture(), eq(response));
        Enumeration<Locale> localeEnumeration = requestCaptor.getValue().getLocales();
        while (localeEnumeration.hasMoreElements()) {
            assertTrue(locales.contains(localeEnumeration.nextElement()));
        }
    }

    private void testLocale(Locale locale) throws ServletException, IOException {
        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        verify(filterChain).doFilter(requestCaptor.capture(), eq(response));
        HttpServletRequest request = requestCaptor.getValue();
        assertEquals(locale, request.getLocale());
    }

    private Enumeration<Locale> createEnumeration() {
        return Collections.enumeration(locales);
    }

}