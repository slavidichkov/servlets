package com.clouway.adapter.persistence.inmemory;

import com.clouway.core.UsersRepository;
import com.clouway.core.UsersRepositoryFactory;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class InmemoryUserRepositoryFactory implements UsersRepositoryFactory {
    public UsersRepository getUserRepository() {
        return new InmemoryUserRepository();
    }
}
