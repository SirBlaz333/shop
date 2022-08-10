package com.my.web.filter.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamParser {
    private final static String PARAM_REGEX = "(^|, )([a-zA-Z_]+)";
    private final static int PARAM_GROUP = 2;

    public List<String> getParams(String params){
        List<String> localeList = new ArrayList<>();
        Pattern pattern = Pattern.compile(PARAM_REGEX);
        Matcher matcher = pattern.matcher(params);
        while(matcher.find()){
            String param = matcher.group(PARAM_GROUP);
            localeList.add(param);
        }
        return localeList;
    }
}
