package com.clouway.http;

import com.clouway.core.*;
import com.clouway.http.authorization.CookieSessionFinder;
import com.google.common.base.Optional;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Home extends HttpServlet{
  private CurrentUserProvider currentUserProvider = DependencyManager.getDependency(CurrentUserProvider.class);
  private LoggedUsersRepositoryFactory loggedUsersRepositoryFactory=DependencyManager.getDependency(LoggedUsersRepositoryFactory.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Optional<CurrentUser> currentUser = currentUserProvider.get(new CookieSessionFinder(req.getCookies()));
    printPage(resp.getWriter(),currentUser);
  }

  private void printPage(PrintWriter writer, Optional<CurrentUser> currentUser) {
    LoggedUsersRepository loggedUsersRepository=loggedUsersRepositoryFactory.getLoggedUsersRepository();

    Configuration cfg = new Configuration();
    cfg.setClassForTemplateLoading(Home.class, "");
    cfg.setIncompatibleImprovements(new Version(2, 3, 20));
    cfg.setDefaultEncoding("UTF-8");
    cfg.setLocale(Locale.US);
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    Map<String, Object> input = new HashMap<String, Object>();
    input.put("loggedUsers", loggedUsersRepository.getCount());

    if (!currentUser.isPresent()) {
      input.put("links","<a href=\"login\">Login</a> </br> <a href=\"registration\">Registration</a>");
    } else {
      input.put("links","<a href=\"balance\">Account manager</a> </br> <a href=\"logout\">Logout</a>");
    }

    Template template=null;
    try {
      template = cfg.getTemplate("home.ftl");
      template.process(input, writer);
    } catch (TemplateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
