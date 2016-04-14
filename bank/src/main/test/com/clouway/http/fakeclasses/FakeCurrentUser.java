package com.clouway.http.fakeclasses;

import com.clouway.core.CurrentUser;
import com.clouway.core.SessionFinder;
import com.clouway.core.User;
import com.google.common.base.Optional;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class FakeCurrentUser implements CurrentUser{
    private User user;
    private String sid;

    public void setUser(User user){
        this.user = user;
    }

    public Optional<User> getUser() {
        if (user!=null){
            return Optional.of(user);
        }
        return Optional.absent();
    }

    public void set(SessionFinder sessionFinder) {

    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
