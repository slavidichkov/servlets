package com.clouway.http;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PagePresenterTest {
    private PagePresenter pagePresenter;
    private FakeRequest request;
    private FakeResponse response;

    @Before
    public void setUp() throws Exception {
        pagePresenter = new PagePresenter();
        request =new FakeRequest();
        response = new FakeResponse();
    }

    @Test
    public void receiveRequestFromRouter() throws IOException, ServletException {
        request.setAttribute("page","first");

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        pagePresenter.doPost(request, response);
        String expected = out.toString();
        assertThat(expected, containsString("first"));
    }

}