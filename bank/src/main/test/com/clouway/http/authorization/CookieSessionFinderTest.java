package com.clouway.http.authorization;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CookieSessionFinderTest {
  CookieSessionFinder cookieSessionFinder;

  @Before
  public void setUp(){
    Cookie[] cookies = new Cookie[]{new Cookie("sid","this is session id"),new Cookie("dadad","dadadadadadda")};
    cookieSessionFinder=new CookieSessionFinder(cookies);
  }

  @Test
  public void getId() {
    String sid=cookieSessionFinder.getId();
      assertThat(sid,is(equalTo("this is session id")));
  }

}