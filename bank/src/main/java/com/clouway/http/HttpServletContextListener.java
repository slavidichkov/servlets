package com.clouway.http;

import com.clouway.adapter.persistence.sql.SqlAccountsRepositoryFactory;
import com.clouway.adapter.persistence.sql.SqlSessionsRepositoryFactory;
import com.clouway.adapter.persistence.sql.SqlUsersRepositoryFactory;
import com.clouway.core.*;
import com.clouway.http.authorization.SecurityFilter;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.EnumSet;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class HttpServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DependencyManager.addDependencies(AccountsRepositoryFactory.class, new SqlAccountsRepositoryFactory());
        DependencyManager.addDependencies(UsersRepositoryFactory.class, new SqlUsersRepositoryFactory());
        DependencyManager.addDependencies(SessionsRepositoryFactory.class, new SqlSessionsRepositoryFactory());
        DependencyManager.addDependencies(Time.class,new TimeImpl());
        DependencyManager.addDependencies(UIDGenerator.class,new UIDGeneratorImpl());
        DependencyManager.addDependencies(UserValidator.class,new RegularExpressionUserValidator());
        DependencyManager.addDependencies(CurrentUser.class,new CurrentUserImpl());

        ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.addServlet("login", new Login()).addMapping("/login");
        servletContext.addFilter("securityFilter", new SecurityFilter()).addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST),true,"balance");
        servletContext.addServlet("logout", new Logout()).addMapping("/logout");
        servletContext.addServlet("registration", new Register()).addMapping("/registration");
        servletContext.addServlet("balance", new AccountManager()).addMapping("/balance");
        servletContext.addServlet("home", new Home()).addMapping("/");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
