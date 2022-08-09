package com.my.web.locale;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocaleParser {
    private final static String LOCALE_REGEX = "(^|, )([a-zA-Z_]+)";
    private final static int LOCALE_GROUP = 2;

    public List<Locale> getLocales(String localeParam){
        List<Locale> localeList = new ArrayList<>();
        Pattern pattern = Pattern.compile(LOCALE_REGEX);
        Matcher matcher = pattern.matcher(localeParam);
        while(matcher.find()){
            Locale locale = new Locale(matcher.group(LOCALE_GROUP));
            localeList.add(locale);
        }
        return localeList;
    }
}
