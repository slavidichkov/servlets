package com.clouway.http;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class FakeRequestDispatcher implements RequestDispatcher{
    private ServletRequest request;
    private ServletResponse response;



    public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
    }

    public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {

    }

    public ServletRequest getRequest() {
        return request;
    }

    public ServletResponse getResponse() {
        return response;
    }
}
