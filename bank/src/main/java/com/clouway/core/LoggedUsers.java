package com.clouway.core;

import com.clouway.core.User;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class LoggedUsers {
    private static Set<String> emails=new HashSet<String>();

    public static void login(User user){
        emails.add(user.email);
    }

    public static void logout(User user){
        emails.remove(user.email);
    }

    public static int getCount(){
        return emails.size();
    }
}
