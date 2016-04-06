package com.clouway.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class LinkAccessCounter extends HttpServlet {
    private final Map<String, Integer> linkCounters;

    public LinkAccessCounter(Map<String, Integer> linkCounters) {
        this.linkCounters = linkCounters;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String linkId = req.getParameter("linkId");
        HttpSession session = req.getSession();
        Map<String, Integer> linkCounters = (Map<String, Integer>) session.getAttribute("linkCounters");
        if (linkCounters == null) {
            linkCounters=new HashMap<String, Integer>();
            linkCounters.putAll(this.linkCounters);
            session.setAttribute("linkCounters", linkCounters);
        }
        if (linkCounters.containsKey(linkId)) {
            Integer counter = linkCounters.get(linkId);
            linkCounters.put(linkId, counter + 1);
        }
        PrintWriter writer = resp.getWriter();
        writer.println(getHtmlContent(linkCounters));
        writer.flush();
    }

    private String getHtmlContent(Map<String, Integer> linkCounters) {
        StringBuilder stringBuilder=new StringBuilder();

        String prefix = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Local Page</title>" +
                "</head>" +
                "<body>" +
                "<ul>";

        String sufix = "</ul>" +
                "</body>" +
                "</html>";

        stringBuilder.append(prefix);

        for (String key : linkCounters.keySet()) {
            stringBuilder.append("    <li><a name=" + key + " href=\"/clickcounter?linkId=" + key + "\"><span>" + key +" "+ linkCounters.get(key) + "</span></a></li>");
        }

        stringBuilder.append(sufix);

        return stringBuilder.toString();

    }
}
