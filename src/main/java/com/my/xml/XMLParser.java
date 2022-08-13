package com.my.xml;

import com.my.xml.exception.XMLParsingException;

public interface XMLParser {
    Object parse(String filepath) throws XMLParsingException;
}
