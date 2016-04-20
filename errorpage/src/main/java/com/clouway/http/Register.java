package com.clouway.http;

import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.UserValidator;
import com.clouway.core.ValidationUser;
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

import static com.clouway.core.ValidationUser.newValidationUser;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Register extends HttpServlet{
  private final UserRepository userRepository;
  private final UserValidator userValidator;

  public Register(UserRepository userRepository, UserValidator userValidator) {
    this.userRepository = userRepository;
    this.userValidator = userValidator;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    printPage(resp.getWriter(), new HashMap<String,String>());
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String name = req.getParameter("name");
    String password = req.getParameter("password");
    String confirmPassword = req.getParameter("confirmPassword");
    String nickName = req.getParameter("nickName");
    String email = req.getParameter("email");
    String city = req.getParameter("city");
    String age = req.getParameter("age");

    ValidationUser validationUser = newValidationUser()
            .name(name,"wrongName")
            .nickName(nickName,"wrongNickName")
            .email(email,"wrongEmail")
            .password(password,"wrongPassword")
            .confirmPassword(confirmPassword,"wrongConfirmPassword")
            .city(city,"wrongCity")
            .age(age,"wrongAge")
            .build();

    Map<String, String> errors = userValidator.validate(validationUser);
    if (!errors.isEmpty()) {
      printPage(resp.getWriter(), errors);
      return;
    }
    Optional<User> optUser = userRepository.getUser(email);
    if (!optUser.isPresent()) {
      User user = new User(name, nickName, email, password, city, new Integer(age));
      userRepository.register(user);
      resp.sendRedirect("/login");
    } else {
      printPage(resp.getWriter(), new HashMap<String, String>() {{
        put("wrongEmail", "user already exists");
      }});
    }
  }

  private void printPage(PrintWriter writer, Map<String, String> errors) {
    Configuration cfg = new Configuration();
    cfg.setClassForTemplateLoading(Register.class, "");
    cfg.setIncompatibleImprovements(new Version(2, 3, 20));
    cfg.setDefaultEncoding("UTF-8");
    cfg.setLocale(Locale.US);
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    Map<String, Object> input = new HashMap<String, Object>();
    input.put("wrongName", "");
    input.put("wrongNickName", "");
    input.put("wrongEmail", "");
    input.put("wrongPassword", "");
    input.put("wrongConfirmPassword", "");
    input.put("wrongCity", "");
    input.put("wrongAge", "");
    input.putAll(errors);

    Template template=null;
    try {
      template = cfg.getTemplate("register.ftl");
      template.process(input, writer);
    } catch (TemplateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
