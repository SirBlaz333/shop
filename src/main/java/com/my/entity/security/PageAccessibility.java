package com.my.entity.security;

import com.my.entity.user.UserRole;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageAccessibility {
    private final Map<String, List<UserRole>> accessibility;

    public PageAccessibility() {
        accessibility = new ConcurrentHashMap<>();
    }

    public void put(String pagePattern, List<UserRole> userRoles) {
        accessibility.put(pagePattern, userRoles);
    }

    public List<UserRole> get(String page) {
        for (String pagePattern : accessibility.keySet()) {
            if (page.matches(pagePattern)) {
                return accessibility.get(pagePattern);
            }
        }
        return Collections.emptyList();
    }

    public boolean pageIsRestricted(String page){
        for (String pagePattern : accessibility.keySet()) {
            if (page.matches(pagePattern)) {
                return true;
            }
        }
        return false;
    }
}
