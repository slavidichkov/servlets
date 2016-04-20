package com.clouway.http;

import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.UserValidator;
import com.clouway.core.ValidationUser;
import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeSession;
import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static com.clouway.core.ValidationUser.newValidationUser;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class LoginTest{
  private FakeRequest request;
  private FakeResponse response;
  private FakeSession session;
  private Login login;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  UserValidator userValidator;

  @Mock
  UserRepository userRepository;

  @Before
  public void setUp() {
    session=new FakeSession();
    request = new FakeRequest(session);
    response = new FakeResponse();
    login=new Login(userRepository, userValidator);
  }
  @Test
  public void registeredUser() throws ServletException, IOException {
    final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);

    request.setParameter("email", user.email);
    request.setParameter("password", user.password);

    final ValidationUser validationUser = newValidationUser()
            .email(user.email, "wrongEmail")
            .password(user.password, "wrongPassword")
            .build();

    context.checking(new Expectations() {{
      oneOf(userValidator).validate(validationUser);
      will(returnValue(new HashMap<String, String>()));
      oneOf(userRepository).getUser("ivan@abv.bg");
      will(returnValue(Optional.of(user)));
    }});

    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    response.setOutputStream(out);

    login.doPost(request, response);

    assertThat((User) request.getSession().getAttribute("currentUser"), is(user));
    assertThat(response.getRedirectUrl(), is(equalTo("/home")));
  }

  @Test
  public void notRegisteredUser() throws IOException, ServletException {
    final String email="nikola3423@abv.bg";
    final String password="fdafafsasf";
    request.setParameter("email", email);
    request.setParameter("password", password);

    final ValidationUser validationUser = newValidationUser()
            .email(email, "wrongEmail")
            .password(password, "wrongPassword")
            .build();

    context.checking(new Expectations() {{
      oneOf(userValidator).validate(validationUser);
      will(returnValue(new HashMap<String, String>()));
      oneOf(userRepository).getUser("nikola3423@abv.bg");
      will(returnValue(Optional.absent()));
    }});

    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    response.setOutputStream(out);

    login.doPost(request, response);

    String expected = out.toString();
    assertThat(expected.contains("Wrong email or password"), is(true));
  }

}