package com.clouway.server;

import com.clouway.http.PageRecognizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.Set;

public class Jetty {

    private final Server server;

    public Jetty(int port) {
        this.server = new Server(port);
    }

    public void start() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addEventListener(new ServletContextListener() {

            public void contextInitialized(ServletContextEvent servletContextEvent) {
                ServletContext servletContext = servletContextEvent.getServletContext();
                Set<String> pages=new HashSet<String>(){{
                    add("first");
                    add("second");
                    add("third");
                }};
                servletContext.addServlet("recognition", new PageRecognizer(pages)).addMapping("/recognition");
            }

            public void contextDestroyed(ServletContextEvent servletContextEvent) {

            }
        });

        server.setHandler(context);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}