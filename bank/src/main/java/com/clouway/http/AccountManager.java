package com.clouway.http;

import com.clouway.adapter.persistence.sql.DatabaseException;
import com.clouway.core.*;
import com.clouway.http.authorization.CookieSessionFinder;
import com.google.common.base.Optional;

import freemarker.template.*;
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
public class AccountManager extends HttpServlet {
    private final AccountsRepositoryFactory accountsRepositoryFactory =DependencyManager.getDependency(AccountsRepositoryFactory.class);
    private final CurrentUserProvider currentUserProvider =DependencyManager.getDependency(CurrentUserProvider.class);
    private LoggedUsersRepositoryFactory loggedUsersRepositoryFactory=DependencyManager.getDependency(LoggedUsersRepositoryFactory.class);
    private final String amountErrorMessage = " amount is not correct";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, DatabaseException {
        CurrentUser currentUser = currentUserProvider.get(new CookieSessionFinder(req.getCookies())).get();
        Double userBalance = accountsRepositoryFactory.getAccountRepository().getBalance(currentUser.getUser());
        printPage(resp.getWriter(), userBalance,"");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        AccountsRepository accountsRepository= accountsRepositoryFactory.getAccountRepository();

        String transactionType = req.getParameter("transactionType");
        String amount = req.getParameter("amount");

        CurrentUser currentUser = currentUserProvider.get(new CookieSessionFinder(req.getCookies())).get();
        User user = currentUser.getUser();

        String errorMessage=amountErrorMessage;
        if (isValidAmount(amount) && "withdraw".equals(transactionType)) {
            errorMessage="";
            try {
                accountsRepository.withdraw(user, Double.valueOf(amount));
            } catch (InsufficientAvailability ex) {
                errorMessage = "Can not withdraw the given amount";
            }
        }
        if (isValidAmount(amount) && "deposit".equals(transactionType)) {
            errorMessage="";
            accountsRepository.deposit(user, Double.valueOf(amount));

        }
        printPage(resp.getWriter(),accountsRepository.getBalance(user), errorMessage);
    }

    private boolean isValidAmount(String amount){
        return amount.matches("([1-9]{1}[0-9]{0,3}([.][0-9]{2}))|([1-9]{1}[0-9]{0,4})");
    }

    private void printPage(PrintWriter writer, Double balance,String errorMessage) {
        LoggedUsersRepository loggedUsersRepository=loggedUsersRepositoryFactory.getLoggedUsersRepository();

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(AccountManager.class, "");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("loggedUsers", loggedUsersRepository.getCount());
        input.put("balance", balance);
        input.put("errorMessage", errorMessage);

        Template template=null;
        try {
            template = cfg.getTemplate("accountManager.ftl");
            template.process(input, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
