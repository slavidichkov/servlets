package com.clouway.http;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Router extends HttpServlet {
    private String message="";
    private Set<String> pages = new HashSet<String>();

    public Router(Set<String> pages, String message) {
        this.pages = pages;
        this.message = message;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageName = (String) req.getAttribute("page");
        if (pages.contains(pageName)) {
            req.setAttribute("page",message+pageName);
            RequestDispatcher dispatcher = req.getRequestDispatcher("pagePresenter");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
