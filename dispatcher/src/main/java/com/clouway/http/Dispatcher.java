package com.clouway.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Dispatcher extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String visitedPagesAttrName = "visitedPages";

        final String visitedPage = req.getParameter("visitedPage");
        PrintWriter writer = resp.getWriter();

        Set<String> visitedPages = (Set<String>) session.getAttribute(visitedPagesAttrName);
        if (visitedPages == null) {
            session.setAttribute(visitedPagesAttrName, new HashSet<String>() {{
                add(visitedPage);
            }});
            printPage(writer, "Welcome on: " + visitedPage);
            return;
        }
        if (visitedPages.contains(visitedPage)) {
            printPage(writer, visitedPage);
        } else {
            visitedPages.add(visitedPage);
            printPage(writer, "Welcome on: " + visitedPage);
        }
    }

    private void printPage(PrintWriter writer, String pageName) {
        writer.println(getHtmlContent(pageName));
        writer.flush();
    }

    private String getHtmlContent(String pageName) {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>" + pageName + "</title>" +
                "</head>" +
                "<body>" +
                "<h1> <h2>" + pageName + "</h2></h1>" +
                "" +
                "</body>" +
                "</html>";
    }
}
