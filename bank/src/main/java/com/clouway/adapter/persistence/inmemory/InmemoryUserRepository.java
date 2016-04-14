package com.clouway.adapter.persistence.inmemory;

import com.clouway.core.User;
import com.clouway.core.UsersRepository;
import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
class InmemoryUserRepository implements UsersRepository {
    private static Map<String,User> users=new HashMap<String, User>(){{
        put("admin@abv.bg", new User("admin","admin", "admin@abv.bg","admin1","sliven",23));
    }};

    public void register(User user) {
        users.put(user.email,user);
    }

    public Optional<User> getUser(String email) {
        User user = users.get(email);
        return Optional.of(user);
    }
}
