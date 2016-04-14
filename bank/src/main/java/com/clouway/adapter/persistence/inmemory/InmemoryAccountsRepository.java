package com.clouway.adapter.persistence.inmemory;

import com.clouway.core.AccountsRepository;
import com.clouway.core.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class InmemoryAccountsRepository implements AccountsRepository {
    private static Map<String, Double> balances = new HashMap<String, Double>();

    public Double getBalance(User user) {
        if (balances.containsKey(user.email)){
            return balances.get(user.email);
        }
        return new Double(0);
    }

    public Double deposit(User user, Double amount) {
        String email = user.email;
        Double balance;
        if (!balances.containsKey(email)){
            balance=new Double(0);
        }else {
            balance = balances.get(email);
        }
        Double newBalance = balance + amount;
        balances.put(email, newBalance);
        return newBalance;
    }

    public Double withdraw(User user, Double amount) {
        String email = user.email;
        Double balance;
        if (!balances.containsKey(email)){
            balance=new Double(0);
            return balance;
        }
        balance = balances.get(email);
        Double newBalance = balance - amount;
        balances.put(email, newBalance);
        return newBalance;
    }

    public void register(User user) {
        if (!balances.containsKey(user.email)){
            balances.put(user.email, new Double(0));
        }
    }
}
