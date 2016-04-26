package com.clouway.http.fakeclasses;

import com.clouway.core.CurrentUser;
import com.clouway.core.CurrentUserProvider;
import com.clouway.core.SessionFinder;
import com.clouway.core.User;
import com.google.common.base.Optional;

/**
 * Created by admin on 26.4.2016 г..
 */
public class FakeCurrentUserProvider implements CurrentUserProvider {
    private User user;
    private String sessionID;

    public Optional<CurrentUser> get(SessionFinder sessionFinder) {
        if (sessionID!=null && user!=null){
            return Optional.of(new CurrentUser(user,sessionID));
        }
        return Optional.absent();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
