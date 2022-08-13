package com.my.service.user;

import com.my.entity.user.UserRole;

public class UserRoleParser {
    public UserRole getUserRole(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.toString().equals(role.toUpperCase())) {
                return userRole;
            }
        }
        return null;
    }
}
