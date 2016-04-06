package com.clouway.http;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class HttpServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map<String,Integer> linkCounters=new HashMap<String, Integer>(){{
            put("FirstLink", 0);
            put("SecondLink", 0);
            put("ThirdLink", 0);
        }};
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.addServlet("clickcounter", new LinkAccessCounter(linkCounters)).addMapping("/clickcounter");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
