package com.my.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

public class LocaleListTag extends SimpleTagSupport {
    private String var;
    private Enumeration<Locale> enumeration;

    public void setEnumeration(Enumeration<Locale> enumeration) {
        this.enumeration = enumeration;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter jspWriter = getJspContext().getOut();
        while (enumeration.hasMoreElements()) {
            getJspContext().setAttribute(var, enumeration.nextElement());
            getJspBody().invoke(jspWriter);
        }
    }
}
