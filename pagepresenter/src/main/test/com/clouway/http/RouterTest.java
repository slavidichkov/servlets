package com.clouway.http;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class RouterTest {
    private Router router;
    private FakeRequest request;
    private FakeResponse response;
    private FakeRequestDispatcher requestDispatcher;

    @Before
    public void setUp() throws Exception {
        Set<String> pages = new HashSet<String>(){{
            add("first");
            add("second");
            add("third");
        }};
        router = new Router(pages,"Request is send from: ");
        requestDispatcher=new FakeRequestDispatcher();
        request =new FakeRequest(requestDispatcher);
        response = new FakeResponse();
    }

    @Test
    public void  routeReceivedRequest() throws IOException, ServletException {
        request.setAttribute("page", "first");

        router.doGet(request, response);

        String expected= (String) request.getAttribute("page");
        assertThat(expected,is("Request is send from: first"));
        assertThat(request.getForwardedTo(), is("pagePresenter"));
    }

    @Test
    public void  anotherRouteReceivedRequest() throws IOException, ServletException {
        request.setAttribute("page", "second");

        router.doGet(request, response);

        String expected= (String) request.getAttribute("page");
        assertThat(expected,is("Request is send from: second"));
        assertThat(request.getForwardedTo(), is("pagePresenter"));
    }
}