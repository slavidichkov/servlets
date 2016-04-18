package com.clouway.http;

import com.clouway.adapter.persistence.sql.DatabaseException;
import com.clouway.core.LoggedUsers;
import com.clouway.core.*;
import com.clouway.http.authorization.CookieSessionFinder;
import com.google.common.base.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class AccountManager extends HttpServlet {
    private final AccountsRepositoryFactory accountsRepositoryFactory =DependencyManager.getDependency(AccountsRepositoryFactory.class);
    private final CurrentUserProvider currentUserProvider =DependencyManager.getDependency(CurrentUserProvider.class);
    private final String amountErrorMessage = " amount is not correct";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        CurrentUser currentUser = currentUserProvider.get(new CookieSessionFinder(req.getCookies()));
        Optional<User> optUser = currentUser.getUser();
        User user= optUser.get();
        Double userBalance = null;
        try{
            userBalance = accountsRepositoryFactory.getAccountRepository().getBalance(user);
        }catch (DatabaseException dex){
            resp.sendRedirect("/errorPage");
        }
         printPage(resp.getWriter(), userBalance,"");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        AccountsRepository accountsRepository= accountsRepositoryFactory.getAccountRepository();

        String transactionType = req.getParameter("transactionType");
        String amount = req.getParameter("amount");

        CurrentUser currentUser = currentUserProvider.get(new CookieSessionFinder(req.getCookies()));
        Optional<User> optUser = currentUser.getUser();
        User user= optUser.get();

        String errorMessage=amountErrorMessage;
        if (isValidAmount(amount) && "withdraw".equals(transactionType)) {
            errorMessage="";
            try {
                accountsRepository.withdraw(user, Double.valueOf(amount));
            } catch (InsufficientAvailability ex) {
                errorMessage = ex.getMessage();
            }catch (DatabaseException dex){
                resp.sendRedirect("/errorPage");
            }
        }
        if (isValidAmount(amount) && "deposit".equals(transactionType)) {
            errorMessage="";
            try{
                accountsRepository.deposit(user, Double.valueOf(amount));
            }catch (DatabaseException dex){
                resp.sendRedirect("/errorPage");
            }

        }
        printPage(resp.getWriter(),accountsRepository.getBalance(user), errorMessage);
    }

    private boolean isValidAmount(String amount){
        return amount.matches("([1-9]{1}[0-9]{0,3}([.][0-9]{2}))|([1-9]{1}[0-9]{0,4})");
    }

    private void printPage(PrintWriter writer, Double balance,String errorMessage) {
        writer.println(getHtmlContent(balance, errorMessage));
        writer.flush();
    }

    private String getHtmlContent(Double balance,String errorMessage) {
        if (balance==null){
            balance=new Double(0);
        }
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>AccountManager</title>" +
                "</head>" +
                "<body>" +
                "<h1>Users in the system : " + LoggedUsers.getCount() + "</h1><br>" +
                "<a href=\"logout\">LOGOUT</a>" +
                "<h1>Balance is : " + balance + "</h1><br>" +
                "" +
                "<form action=\"balance\" name=\"balanceForm\" method=\"post\">" +
                "<input type=\"text\" name=\"amount\" placeholder=\"amount\">" +
                "<span>" + errorMessage + "</span> <br>" +
                "<input type=\"submit\" name=\"transactionType\" value=\"withdraw\">" +
                "<input type=\"submit\" name=\"transactionType\" value=\"deposit\">" +
                "</form>" +
                "</body>" +
                "</html>";
    }
}
