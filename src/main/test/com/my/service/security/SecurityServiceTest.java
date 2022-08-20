package com.my.service.security;

import com.my.entity.security.PageAccessibility;
import com.my.entity.user.UserRole;
import com.my.service.security.impl.SecurityServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SecurityServiceTest {
    private SecurityService securityService;

    @Before
    public void setUp(){
        PageAccessibility pageAccessibility = new PageAccessibility();
        List<UserRole> allRoles = new ArrayList<>();
        allRoles.add(UserRole.USER);
        allRoles.add(UserRole.ADMIN);
        List<UserRole> onlyAdmin = new ArrayList<>();
        onlyAdmin.add(UserRole.ADMIN);
        pageAccessibility.put(".*/user.*", allRoles);
        pageAccessibility.put(".*/admin.*", onlyAdmin);
        securityService = new SecurityServiceImpl(pageAccessibility);
    }

    @Test
    public void testUserPage(){
        String url = "http://localhost:9090/shop/user.jsp";
        assertTrue(securityService.pageIsRestricted(url));
        assertTrue(securityService.userHasAccess(UserRole.ADMIN, url));
        assertTrue(securityService.userHasAccess(UserRole.USER, url));
    }

    @Test
    public void testUserFolder(){
        String url = "http://localhost:9090/shop/user/index.jsp";
        assertTrue(securityService.pageIsRestricted(url));
        assertTrue(securityService.userHasAccess(UserRole.ADMIN, url));
        assertTrue(securityService.userHasAccess(UserRole.USER, url));
    }

    @Test
    public void testAdminPage(){
        String url = "http://localhost:9090/shop/admin.jsp";
        assertTrue(securityService.pageIsRestricted(url));
        assertTrue(securityService.userHasAccess(UserRole.ADMIN, url));
        assertFalse(securityService.userHasAccess(UserRole.USER, url));
    }

    @Test
    public void testAdminFolder(){
        String url = "http://localhost:9090/shop/admin/index.jsp";
        assertTrue(securityService.pageIsRestricted(url));
        assertTrue(securityService.userHasAccess(UserRole.ADMIN, url));
        assertFalse(securityService.userHasAccess(UserRole.USER, url));
    }

    @Test
    public void testNonRestrictedPage(){
        String url = "http://localhost:9090/shop/index.jsp";
        assertFalse(securityService.pageIsRestricted(url));
    }
}