package com.clouway.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PagePresenter extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageName= (String) req.getAttribute("page");
        printPage(resp.getWriter(),pageName);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
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
                "    <title>Page Presenter</title>" +
                "</head>" +
                "<body>" +
                "<h1>" + pageName + " page</h1>" +
                "" +
                "</body>" +
                "</html>";
    }
}
