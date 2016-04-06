package com.clouway.http;

import com.clouway.core.StringCalculator;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CalculatorTest {
    private Calculator calculator;
    private FakeRequest request;
    private FakeResponse response;

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    StringCalculator stringCalculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator(stringCalculator);
        request =new FakeRequest();
        response = new FakeResponse();
    }

    @Test
    public void addOneCharacter() throws IOException, ServletException {
        request.setParameter("clickedButton","4");
        request.setParameter("display","");


        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        calculator.doPost(request, response);
        String expected = out.toString();
        assertThat(expected,containsString("4"));
    }

    @Test
    public void deleteLastCharacter() throws IOException, ServletException {
        request.setParameter("clickedButton","C");
        request.setParameter("display","12345");

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        calculator.doPost(request, response);
        String expected = out.toString();
        assertThat(expected,containsString("1234"));
        assertThat(expected.contains("12345"),is(false));
    }

    @Test
    public void deleteAllCharactersFromDIsplay() throws IOException, ServletException {
        request.setParameter("clickedButton","CE");
        request.setParameter("display","3452");

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        calculator.doPost(request, response);
        String expected = out.toString();
        assertThat(expected.contains("3452"),is(false));
    }
    @Test
    public void equal() throws IOException, ServletException {
        request.setParameter("clickedButton","=");
        request.setParameter("display","5+4");

        context.checking(new Expectations() {{
            oneOf(stringCalculator).eval("5+4");
            will(returnValue("9"));
        }});

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        calculator.doPost(request, response);
        String expected = out.toString();
        assertThat(expected.contains("5+4"),is(false));
        assertThat(expected,containsString("9"));
    }
}