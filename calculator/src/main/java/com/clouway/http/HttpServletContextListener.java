package com.clouway.http;

import com.clouway.core.SimpleStringCalculator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class HttpServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.addServlet("calculator", new Calculator(new SimpleStringCalculator())).addMapping("/calculator");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
