package com.clouway.http;

import com.clouway.adapter.persistence.sql.PersistentAccountsRepositoryFactory;
import com.clouway.adapter.persistence.sql.PersistentLoggedUsersRepository;
import com.clouway.adapter.persistence.sql.PersistentLoggedUsersRepositoryFactory;
import com.clouway.adapter.persistence.sql.PersistentSessionsRepositoryFactory;
import com.clouway.adapter.persistence.sql.PersistentUsersRepositoryFactory;
import com.clouway.core.*;
import com.clouway.http.authorization.SecurityFilter;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class HttpServletContextListener implements ServletContextListener {
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    DependencyManager.addDependencies(AccountsRepositoryFactory.class, new PersistentAccountsRepositoryFactory());
    DependencyManager.addDependencies(UsersRepositoryFactory.class, new PersistentUsersRepositoryFactory());
    DependencyManager.addDependencies(SessionsRepositoryFactory.class, new PersistentSessionsRepositoryFactory());
    DependencyManager.addDependencies(Time.class, new TimeImpl());
    DependencyManager.addDependencies(UIDGenerator.class, new UIDGeneratorImpl());
    DependencyManager.addDependencies(UserValidator.class, new RegularExpressionUserValidator());
    DependencyManager.addDependencies(CurrentUserProvider.class, new CurrentUserProviderImpl());
    DependencyManager.addDependencies(LoggedUsersRepositoryFactory.class,new PersistentLoggedUsersRepositoryFactory());

    ServletContext servletContext = servletContextEvent.getServletContext();

    servletContext.addServlet("login", new Login()).addMapping("/login");
    servletContext.addFilter("securityFilter", new SecurityFilter(new HashSet<String>() {{
      add("login");
      add("registration");
    }})).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    servletContext.addServlet("logout", new Logout()).addMapping("/logout");
    servletContext.addServlet("registration", new Register()).addMapping("/registration");
    servletContext.addServlet("balance", new AccountManager()).addMapping("/balance");
    servletContext.addServlet("home", new Home()).addMapping("/");
  }

  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }
}
