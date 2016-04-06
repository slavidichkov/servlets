package com.clouway.http;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DispatcherTest {
    private Dispatcher dispatcher;
    private FakeSession fakeSession;
    private FakeRequest request;
    private FakeResponse response;

    @Before
    public void setUp() throws Exception {
        dispatcher = new Dispatcher();
        fakeSession = new FakeSession();
        request =new FakeRequest();
        response = new FakeResponse();
    }

    @Test
    public void firstInvokeOfFirstPage() throws IOException, ServletException {
        fakeSession.setAttribute("visitedPages", new HashSet<String>());
        request.setSession(fakeSession);
        request.setParameter("first");

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        dispatcher.doGet(request, response);
        String expected = out.toString();
        assertThat(expected,containsString("Welcome on: first"));
    }

    @Test
    public void secondInvokeOfFirstPage() throws IOException, ServletException {
        fakeSession.setAttribute("visitedPages", new HashSet<String>(){{
            add("first");
        }});
        request.setSession(fakeSession);
        request.setParameter("first");

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        dispatcher.doGet(request, response);
        String expected = out.toString();
        assertThat(expected,containsString("first"));
        assertThat(expected,containsString("Welcome on: first"));
    }
}