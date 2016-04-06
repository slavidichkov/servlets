package com.clouway.http;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PageRecognizerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    PageRecognizer pageRecognizer;

    @Before
    public void setUp() throws Exception {
        Set<String> pages=new HashSet<String>(){{
            add("first");
            add("second");
            add("third");
        }};
        pageRecognizer =new PageRecognizer(pages);
    }

    @Test
    public void requestFromFirstPage() throws IOException, ServletException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        context.checking(new Expectations() {{
            oneOf(request).getParameter("page");
            will(returnValue("first"));
            oneOf(response).getWriter();
            will(returnValue(new PrintWriter(out)));
        }});
        pageRecognizer.doGet(request,response);
        String expected=out.toString();
        assertThat(expected, containsString("first page"));
    }

    @Test
    public void requestFromSecondPage() throws IOException, ServletException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        context.checking(new Expectations() {{
            oneOf(request).getParameter("page");
            will(returnValue("second"));
            oneOf(response).getWriter();
            will(returnValue(new PrintWriter(out)));
        }});
        pageRecognizer.doGet(request,response);
        String expected=out.toString();
        assertThat(expected, containsString("second page"));
    }

    @Test
    public void requestFromThirdPage() throws IOException, ServletException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        context.checking(new Expectations() {{
            oneOf(request).getParameter("page");
            will(returnValue("third"));
            oneOf(response).getWriter();
            will(returnValue(new PrintWriter(out)));
        }});
        pageRecognizer.doGet(request,response);
        String expected=out.toString();
        assertThat(expected, containsString("third page"));
    }

}