package com.clouway.adapter.persistence.inmemory;

import com.clouway.core.SessionsRepository;
import com.clouway.core.SessionsRepositoryFactory;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class InmemorySessionRepositoryFactory implements SessionsRepositoryFactory {
    public SessionsRepository getSessionRepository() {
        return new InmemorySessionRepository();
    }
}
