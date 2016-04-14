package com.clouway.http;

import com.clouway.core.*;
import com.google.common.base.Optional;

import javax.servlet.ServletException;
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
public class Register extends HttpServlet {
    private UsersRepositoryFactory usersRepositoryFactory = DependencyManager.getDependency(UsersRepositoryFactory.class);
    private AccountsRepositoryFactory accountsRepositoryFactory= DependencyManager.getDependency(AccountsRepositoryFactory.class);
    private UserValidator validator=DependencyManager.getDependency(UserValidator.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        printPage(writer, new HashMap<String, String>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UsersRepository userRepository = usersRepositoryFactory.getUserRepository();
        AccountsRepository accountsRepository=accountsRepositoryFactory.getAccountRepository();

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

        Map<String, String> errors = validator.validate(validationUser);
        if (!errors.isEmpty()) {
            printPage(resp.getWriter(), errors);
            return;
        }
        Optional<User> optUser = userRepository.getUser(email);
        if (!optUser.isPresent()) {
            User user = new User(name, nickName, email, password, city, new Integer(age));
            userRepository.register(user);
            accountsRepository.register(user);
            resp.sendRedirect("/login");
        } else {
            printPage(resp.getWriter(), new HashMap<String, String>() {{
                put("wrongEmail", "user already exists");
            }});
        }
    }

    private void printPage(PrintWriter writer, Map<String, String> errorMessages) {
        writer.println(getHtmlContent(errorMessages));
        writer.flush();
    }

    private String getHtmlContent(Map<String, String> errors) {
        Map<String, String> errorFields = new HashMap<String, String>() {{
            put("wrongName", "");
            put("wrongNickName", "");
            put("wrongEmail", "");
            put("wrongPassword", "");
            put("wrongConfirmPassword", "");
            put("wrongCity", "");
            put("wrongAge", "");
        }};
        errorFields.putAll(errors);
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Registration</title>" +
                "</head>" +
                "<body>" +
                "<form action=\"registration\" name=\"registrationForm\" method=\"post\">" +
                "<label >Name </label>" +
                "<span>" + errorFields.get("wrongName") + "</span> <br>" +
                "<input type=\"text\" name=\"name\" placeholder=\"Name\"><br>" +
                "<label  >Nick Name </label>" +
                "<span>" + errorFields.get("wrongNickName") + "</span> <br>" +
                "<input type=\"text\" name=\"nickName\" placeholder=\"Nick Name\"><br>" +
                "<label  >Age </label>" +
                "<span>" + errorFields.get("wrongAge") + "</span> <br>" +
                "<input type=\"text\" name=\"age\" placeholder=\"Age\"><br>" +
                "<label  >City </label>" +
                "<span>" + errorFields.get("wrongCity") + "</span> <br>" +
                "<input type=\"text\" name=\"city\" placeholder=\"City\"><br>" +
                "<label >Email </label>" +
                "<span>" + errorFields.get("wrongEmail") + "</span> <br>" +
                "<input type=\"text\" name=\"email\" placeholder=\"Email\"><br>" +
                "<label >Password </label>" +
                "<span>" + errorFields.get("wrongPassword") + "</span> <br>" +
                "<input type=\"password\" name=\"password\" placeholder=\"Password\"><br>" +
                "<label  >Confirm Password </label>" +
                "<span>" + errorFields.get("wrongConfirmPassword") + "</span> <br>" +
                "<input type=\"password\" name=\"confirmPassword\" placeholder=\"Confirm Password\"><br>" +
                "<input type=\"submit\" value=\"Submit\">" +
                "</form>" +
                "</body>" +
                "</html>";
    }
}
