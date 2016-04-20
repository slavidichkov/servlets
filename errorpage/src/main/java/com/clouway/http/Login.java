package com.clouway.http;

import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.UserValidator;
import com.clouway.core.ValidationUser;
import com.google.common.base.Optional;
import freemarker.template.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.clouway.core.ValidationUser.newValidationUser;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Login extends HttpServlet {
  private final UserRepository userRepository;
  private final UserValidator userValidator;

  public Login(UserRepository userRepository, UserValidator userValidator) {
    this.userRepository = userRepository;
    this.userValidator = userValidator;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String email = req.getParameter("email");
    String password = req.getParameter("password");

    ValidationUser validationUser = newValidationUser()
            .email(email, "wrongEmail")
            .password(password, "wrongPassword")
            .build();

    Map<String, String> errors = userValidator.validate(validationUser);
    if (!errors.isEmpty()) {
      printPage(resp.getWriter(), errors);
      return;
    }

    Optional<User> optUser = userRepository.getUser(email);

    if (optUser.isPresent() && password.equals(optUser.get().password)) {
      User user=optUser.get();
      Cookie cookie = new Cookie("sid", user.email);
      req.getSession().setAttribute("currentUser",user);
      resp.sendRedirect("/home");
    } else {
      printPage(resp.getWriter(), new HashMap<String, String>() {{
        put("wrongEmailOrPassword", "Wrong email or password");
      }});
    }
  }


  private void printPage(PrintWriter printWriter, Map<String,String> errorMessage){
    Configuration cfg = new Configuration();
    cfg.setClassForTemplateLoading(Login.class, "");
    cfg.setIncompatibleImprovements(new Version(2, 3, 20));
    cfg.setDefaultEncoding("UTF-8");
    cfg.setLocale(Locale.US);
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    Map<String, Object> input = new HashMap<String, Object>();
    input.put("wrongEmailOrPassword", "");
    input.put("wrongEmail","");
    input.put("wrongPassword","");
    input.putAll(errorMessage);

    Template template=null;
    try {
      template = cfg.getTemplate("login.ftl");
      template.process(input, printWriter);
    } catch (TemplateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
