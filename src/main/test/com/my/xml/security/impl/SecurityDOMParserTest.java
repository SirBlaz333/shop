package com.my.xml.security.impl;

import com.my.entity.security.PageAccessibility;
import com.my.entity.user.UserRole;
import com.my.xml.exception.XMLParsingException;
import com.my.xml.security.SecurityXMLParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SecurityDOMParserTest {
    private PageAccessibility pageAccessibility;

    @Before
    public void setUp() throws XMLParsingException {
        SecurityXMLParser securityXMLParser = new SecurityDOMParser();
        pageAccessibility = securityXMLParser.parse("src/main/test/resources/restrictions.xml");
    }

    @Test
    public void adminPageRestrictionTest() {
        assertTrue(pageAccessibility.pageIsRestricted("/admin/"));
    }

    @Test
    public void userPageRestrictionTest() {
        assertTrue(pageAccessibility.pageIsRestricted("/user/"));
    }

    @Test
    public void rolesForUserPageTest() {
        List<UserRole> rolesForUserPage = new ArrayList<>();
        rolesForUserPage.add(UserRole.USER);
        rolesForUserPage.add(UserRole.ADMIN);
        List<UserRole> list = pageAccessibility.get("/user/");
        assertEquals(rolesForUserPage, list);
    }

    @Test
    public void rolesForAdminPageTest() {
        List<UserRole> rolesForUserPage = new ArrayList<>();
        rolesForUserPage.add(UserRole.ADMIN);
        List<UserRole> list = pageAccessibility.get("/admin/");
        assertEquals(rolesForUserPage, list);
    }
}