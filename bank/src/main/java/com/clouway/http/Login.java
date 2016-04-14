package com.clouway.http;


import com.clouway.core.LoggedUsers;
import com.clouway.core.*;
import com.google.common.base.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.clouway.core.ValidationUser.newValidationUser;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Login extends HttpServlet {
    private final UsersRepositoryFactory userRepositoryFactory = DependencyManager.getDependency(UsersRepositoryFactory.class);
    private final SessionsRepositoryFactory sessionsRepositoryFactory = DependencyManager.getDependency(SessionsRepositoryFactory.class);
    private final UIDGenerator uidGenerator = DependencyManager.getDependency(UIDGenerator.class);
    private final Time time = DependencyManager.getDependency(Time.class);
    private UserValidator validator = DependencyManager.getDependency(UserValidator.class);
    private  final  UsersRepository userRepository = userRepositoryFactory.getUserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        printPage(writer, new HashMap<String, String>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        ValidationUser validationUser = newValidationUser()
                .email(email, "wrongEmail")
                .password(password, "wrongPassword")
                .build();

        Map<String, String> errors = validator.validate(validationUser);
        if (!errors.isEmpty()) {
            printPage(resp.getWriter(), errors);
            return;
        }

        Optional<User> optUser = userRepository.getUser(email);
        if (!optUser.isPresent()) {
            printPage(resp.getWriter(), new HashMap<String, String>() {{
                put("wrongEmailOrPassword", "Wrong email or password");
            }});
            return;
        }
        User user=optUser.get();
        if (password.equals(user.password)) {
            String sid = uidGenerator.randomID();
            SessionsRepository sessionRepository = sessionsRepositoryFactory.getSessionRepository();
            sessionRepository.register(new Session(sid, user.email, time.now().getTime() + Session.sessionExpiresTime));
            Cookie cookie = new Cookie("sid", sid);
            resp.addCookie(cookie);
            LoggedUsers.login(user);
            resp.sendRedirect("/balance");
        } else {
            printPage(resp.getWriter(), new HashMap<String, String>() {{
                put("wrongEmailOrPassword", "Wrong email or password");
            }});
        }
    }

    private void printPage(PrintWriter writer, Map<String, String> errors) {
        writer.println(getHtmlContent(errors));
        writer.flush();
    }

    private String getHtmlContent(Map<String, String> errors) {
        Map<String, String> errorFields = new HashMap<String, String>() {{
            put("wrongEmail", "");
            put("wrongPassword", "");
            put("wrongEmailOrPassword", "");
        }};
        errorFields.putAll(errors);
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Login</title>" +
                "</head>" +
                "<body>" +
                "<form action=\"login\" name=\"loginForm\" method=\"post\">" +
                "<label >Email </label>" +
                "<span>" + errorFields.get("wrongEmail") + "</span> <br>" +
                "<input type=\"text\" name=\"email\" placeholder=\"Email\"><br>" +
                "<label  >Password </label>" +
                "<span>" + errorFields.get("wrongPassword") + "</span> <br>" +
                "<input type=\"password\" name=\"password\" placeholder=\"Password\"><br>" +
                "<input type=\"submit\" value=\"Submit\">" +
                "<p>" + errorFields.get("wrongEmailOrPassword") + "</p>" +
                "</form>" +
                "</body>" +
                "</html>";
    }
}
