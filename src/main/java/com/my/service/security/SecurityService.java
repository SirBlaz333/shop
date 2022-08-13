package com.my.service.security;

import com.my.entity.user.UserRole;

public interface SecurityService {
    boolean userHasAccess(UserRole userRole, String URL);
    boolean pageIsRestricted(String URL);
}
