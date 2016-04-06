package java.com.clouway.http;

import com.clouway.http.LinkAccessCounter;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class LinkAccessCounterTest {


    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private FakeRequest request;
    private FakeResponse response;
    private FakeSession session;

    LinkAccessCounter linkAccessCounter;

    @Before
    public void setUp() throws Exception {
        Map<String,Integer> linkCounters=new HashMap<String, Integer>(){{
            put("FirstLink", 0);
            put("SecondLink", 0);
            put("ThirdLink", 0);
        }};
        linkAccessCounter = new LinkAccessCounter(linkCounters);
        session=new FakeSession();
        request = new FakeRequest(session);
        response = new FakeResponse();
    }

    @Test
    public void oneInvocationOfFirstLink() throws ServletException, IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        request.setParameter("linkId","FirstLink");
        response.setOutputStream(out);

        linkAccessCounter.doGet(request, response);
        String expected=out.toString();
        assertThat(expected.contains("FirstLink 1"),is(true));
    }
    @Test
    public void twoInvocationOfFirstLink() throws ServletException, IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        request.setParameter("linkId","FirstLink");
        response.setOutputStream(out);

        linkAccessCounter.doGet(request, response);
        linkAccessCounter.doGet(request, response);
        String expected=out.toString();
        assertThat(expected.contains("FirstLink 2"),is(true));
    }

    @Test
    public void oneInvocationOfSecondLink() throws ServletException, IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        request.setParameter("linkId","SecondLink");
        response.setOutputStream(out);

        linkAccessCounter.doGet(request, response);
        String expected=out.toString();
        assertThat(expected.contains("SecondLink 1"),is(true));
    }

    @Test
    public void oneInvocationOfThirdLink() throws ServletException, IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        request.setParameter("linkId","ThirdLink");
        response.setOutputStream(out);

        linkAccessCounter.doGet(request, response);
        String expected=out.toString();
        assertThat(expected.contains("ThirdLink 1"),is(true));
    }
}