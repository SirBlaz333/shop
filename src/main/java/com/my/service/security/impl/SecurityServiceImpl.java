package com.my.service.security.impl;

import com.my.entity.security.PageAccessibility;
import com.my.entity.user.UserRole;
import com.my.service.security.SecurityService;

import java.util.List;

public class SecurityServiceImpl implements SecurityService {
    private final PageAccessibility pageAccessibility;

    public SecurityServiceImpl(PageAccessibility pageAccessibility) {
        this.pageAccessibility = pageAccessibility;
    }

    @Override
    public boolean userHasAccess(UserRole userRole, String URL) {
        List<UserRole> userRoles = pageAccessibility.get(URL);
        return userRoles.contains(userRole);
    }

    @Override
    public boolean pageIsRestricted(String URL) {
        return pageAccessibility.pageIsRestricted(URL);
    }
}
