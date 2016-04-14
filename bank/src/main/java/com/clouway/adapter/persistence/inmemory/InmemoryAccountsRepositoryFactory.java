package com.clouway.adapter.persistence.inmemory;

import com.clouway.core.AccountsRepository;
import com.clouway.core.AccountsRepositoryFactory;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class InmemoryAccountsRepositoryFactory implements AccountsRepositoryFactory{
    public AccountsRepository getAccountRepository() {
        return new InmemoryAccountsRepository();
    }
}
