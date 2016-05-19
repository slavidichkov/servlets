package com.clouway.core;

import com.clouway.adapter.persistence.sql.PersistenceModule;
import com.clouway.http.authorization.AuthorizationModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class ApplicationModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new AuthorizationModule());
    install(new PersistenceModule());
  }

  @Provides
  public Time getTime(){
    return new TimeImpl();
  }

  @Provides
  public CurrentUserProvider getCurrentUserProvider(SessionsRepository sessionsRepository,UsersRepository usersRepository){
    return new CurrentUserProviderImpl(sessionsRepository,usersRepository);
  }
}
