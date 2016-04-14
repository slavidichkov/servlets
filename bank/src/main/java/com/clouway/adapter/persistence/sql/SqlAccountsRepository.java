package com.clouway.adapter.persistence.sql;

import com.clouway.core.AccountsRepository;
import com.clouway.core.User;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SqlAccountsRepository implements AccountsRepository{
    private final DataSource dataSource;

    public SqlAccountsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Double getBalance(User user) {
        Connection connection = null;
        Double balance=new Double(0);
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("SELECT balance FROM accounts WHERE userEmail=?");
            statement.setString(1,user.email);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()) {
                balance=resultSet.getDouble("balance");
            }
            return balance;
        } catch (SQLException e) {

        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        balance=new Double(0);
        return balance;
    }

    public Double deposit(User user, Double amount) {
        Double balance=getBalance(user);
        Double newBalance=balance+amount;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("UPDATE accounts SET balance=? WHERE userEmail=?");
            statement.setDouble(1,newBalance);
            statement.setString(2,user.email);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("exeption");
            return new Double(0);
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return newBalance;
    }

    public Double withdraw(User user, Double amount) {
        Double balance=getBalance(user);
        Double newBalance=balance-amount;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("UPDATE accounts SET balance=? WHERE userEmail=?");
            statement.setDouble(1,newBalance);
            statement.setString(2,user.email);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            return new Double(0);
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return newBalance;
    }

    public void register(User user) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("INSERT INTO accounts(userEmail,balance) VALUES (?,?)");
            statement.setString(1,user.email);
            statement.setDouble(2,new Double(0));
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
