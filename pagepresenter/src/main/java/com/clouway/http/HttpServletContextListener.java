package com.clouway.http;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class HttpServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Set<String> pages = new HashSet<String>() {{
            add("first");
            add("second");
            add("third");
        }};

        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.addServlet("first", new FirstPage()).addMapping("/first");
        servletContext.addServlet("second", new SecondPage()).addMapping("/second");
        servletContext.addServlet("third", new ThirdPage()).addMapping("/third");
        servletContext.addServlet("router", new Router(pages, "Request is send from: ")).addMapping("/router");
        servletContext.addServlet("pagePresenter", new PagePresenter()).addMapping("/pagePresenter");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
