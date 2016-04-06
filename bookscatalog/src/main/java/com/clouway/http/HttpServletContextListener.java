package com.clouway.http;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.EventListener;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class HttpServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.addServlet("books", new BooksCatalog()).addMapping("/books");
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
