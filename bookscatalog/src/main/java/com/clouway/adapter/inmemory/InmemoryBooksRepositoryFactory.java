package com.clouway.adapter.inmemory;

import com.clouway.core.*;
import com.clouway.core.BooksRepository;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class InmemoryBooksRepositoryFactory implements BooksRepositoryFactory {
    public BooksRepository getRepository() {
        return new InmemoryBooksRepository();
    }
}
