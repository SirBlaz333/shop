package com.my.entity.response;

import com.my.entity.output.gzip.GzipServletOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GzipResponseWrapper extends HttpServletResponseWrapper {
    public static final String GZIP = "gzip";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String TYPE_TEXT = "text/html";
    private PrintWriter printWriter;
    private GzipServletOutputStream gzipServletOutputStream;
    private boolean canBeCompressed;

    public GzipResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void close() throws IOException {
        if (printWriter != null) {
            printWriter.close();
        }
        if (gzipServletOutputStream != null) {
            gzipServletOutputStream.close();
        }
    }

    @Override
    public void setContentType(String type) {
        canBeCompressed = type.contains(TYPE_TEXT);
        if(canBeCompressed){
            addHeader(CONTENT_ENCODING, GZIP);
        }
        super.setContentType(type);
    }

    @Override
    public void flushBuffer() throws IOException {
        if (printWriter != null) {
            printWriter.flush();
        }
        if (gzipServletOutputStream != null) {
            gzipServletOutputStream.flush();
        }
        super.flushBuffer();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (canBeCompressed) {
            return getCompressedWriter();
        }
        return super.getWriter();
    }

    private PrintWriter getCompressedWriter() throws IOException {
        if (this.printWriter == null && gzipServletOutputStream != null) {
            throw new IllegalStateException("Cannot get output stream. Output stream has already been obtained");
        }
        if (printWriter == null) {
            gzipServletOutputStream = new GzipServletOutputStream(getResponse().getOutputStream());
            printWriter = new PrintWriter(new OutputStreamWriter(gzipServletOutputStream, getResponse().getCharacterEncoding()));
        }
        return printWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if(canBeCompressed){
            return getCompressedOutputStream();
        }
        return super.getOutputStream();
    }

    private ServletOutputStream getCompressedOutputStream() throws IOException {
        if (this.printWriter != null) {
            throw new IllegalStateException("Cannot get output stream. PrintWriter has already been obtained");
        }
        if (gzipServletOutputStream == null) {
            gzipServletOutputStream = new GzipServletOutputStream(getResponse().getOutputStream());
        }
        return gzipServletOutputStream;
    }
}
