package com.clouway.adapter.persistence.sql;

import com.clouway.core.Session;
import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SessionResultSetBuilderTest {
  SessionResultSetBuilder sessionResultSetBuilder;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  ResultSet resultSet;

  @Before
  public void setUp(){
    sessionResultSetBuilder=new SessionResultSetBuilder();
  }

  @Test
  public void getSession() throws SQLException {
    final Session session =new Session("qwertyuiop","ivan@abv.bg",123456789);
    context.checking(new Expectations() {{
      oneOf(resultSet).next();
      will(returnValue(true));
      oneOf(resultSet).getString("ID");
      will(returnValue(session.ID));
      oneOf(resultSet).getString("userEmail");
      will(returnValue(session.userEmail));
      oneOf(resultSet).getLong("sessionExpiresOn");
      will(returnValue(session.sessionExpiresOn));
    }});

    Optional<Session> expectedSession = sessionResultSetBuilder.build(resultSet);
    assertThat(expectedSession.isPresent(),is(true));
  }
}