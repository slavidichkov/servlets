package com.clouway.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PageRecognizer extends HttpServlet {
    private Set<String> pages;

    public PageRecognizer(Set<String> pages) {
       this.pages = pages;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageName = req.getParameter("page");
        if (pages.contains(pageName)) {
            printPage(resp.getWriter(), pageName);
        }
    }

    private void printPage(PrintWriter writer , String pageName) {
        writer.println(getHtmlContent(pageName));
        writer.flush();
    }

    private String getHtmlContent(String pageName) {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Local Page</title>" +
                "</head>" +
                "<body>" +
                "<h1>Request is send from : " + pageName + " page</h1>" +
                "" +
                "</body>" +
                "</html>";
    }
}
