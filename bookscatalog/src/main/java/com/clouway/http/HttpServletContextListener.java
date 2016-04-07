package com.clouway.http;

import com.clouway.adapter.inmemory.InmemoryBooksRepositoryFactory;
import com.clouway.core.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class HttpServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DependencyManager.addDependencies(PageBean.class,new  PageBeanImpl<Book>());
        DependencyManager.addDependencies(BooksRepositoryFactory.class,new InmemoryBooksRepositoryFactory());
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.addServlet("books", new BooksCatalog()).addMapping("/books");
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
