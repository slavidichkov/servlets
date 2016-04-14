package com.clouway.http.authorization;

import com.clouway.core.SessionFinder;

import javax.servlet.http.Cookie;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CookieSessionFinder implements SessionFinder {
    private final Cookie[] cookies;

    public CookieSessionFinder(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public String getId() {
        String sid="";
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if ("sid".equals(cookie.getName())) {
                sid = cookie.getValue();
            }
        }
        return sid;
    }
}
