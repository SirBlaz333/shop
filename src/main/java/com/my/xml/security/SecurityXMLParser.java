package com.my.xml.security;

import com.my.entity.security.PageAccessibility;
import com.my.xml.XMLParser;
import com.my.xml.exception.XMLParsingException;

public interface SecurityXMLParser extends XMLParser {
    @Override
    PageAccessibility parse(String filePath) throws XMLParsingException;
}
