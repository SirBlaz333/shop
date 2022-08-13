package com.my.xml.security.impl;

import com.my.entity.security.PageAccessibility;
import com.my.entity.user.UserRole;
import com.my.service.user.UserRoleParser;
import com.my.xml.exception.XMLParsingException;
import com.my.xml.security.SecurityConstants;
import com.my.xml.security.SecurityXMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityDOMParser implements SecurityXMLParser {
    private static final String EMPTY_STRING = "";
    private final PageAccessibility pageAccessibility;
    private final UserRoleParser userRoleParser;
    public SecurityDOMParser(){
        pageAccessibility = new PageAccessibility();
        userRoleParser = new UserRoleParser();
    }

    @Override
    public PageAccessibility parse(String filepath) throws XMLParsingException {
        try {
            return parseDocument(filepath);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException(e.getMessage());
        }
    }

    private PageAccessibility parseDocument(String filepath) throws IOException, ParserConfigurationException, SAXException {
        Document document = getDocument(filepath);
        NodeList nodeList = document.getElementsByTagName(SecurityConstants.RESTRICTION);
        for (int i = 0; i < nodeList.getLength(); i++) {
            parseRestriction(nodeList.item(i));
        }
        return pageAccessibility;
    }

    private Document getDocument(String filepath) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(filepath);
    }

    private void parseRestriction(Node node){
        NodeList restrictions = node.getChildNodes();
        String url = EMPTY_STRING;
        List<UserRole> userRoles = new ArrayList<>();
        for (int j = 0; j < restrictions.getLength(); j++) {
            Node restriction = restrictions.item(j);
            if (restriction.getNodeName().equals(SecurityConstants.URL_PATTERN)) {
                url = restriction.getTextContent();
            }
            if (restriction.getNodeName().equals(SecurityConstants.ROLE)) {
                userRoles.add(userRoleParser.getUserRole(restriction.getTextContent()));
            }
        }
        pageAccessibility.put(url, userRoles);
    }
}
