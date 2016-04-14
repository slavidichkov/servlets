package com.clouway.http;

import com.clouway.core.LoggedUsers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Home extends HttpServlet{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    printPage(resp.getWriter());
  }

  private void printPage(PrintWriter writer) {
    writer.println(getHtmlContent());
    writer.flush();
  }

  private String getHtmlContent() {

    return "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "    <meta charset=\"UTF-8\">" +
            "    <title>AccountManager</title>" +
            "</head>" +
            "<body>" +
            "<h1>Users in the system : " + LoggedUsers.getCount() + "</h1><br>" +
            "<a href=\"login\">login</a> <br>" +
            "<a href=\"registration\">registration</a>" +
            "</body>" +
            "</html>";
  }
}
