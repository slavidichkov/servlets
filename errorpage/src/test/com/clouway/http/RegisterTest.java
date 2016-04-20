package com.clouway.http;

import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.UserValidator;
import com.clouway.core.ValidationUser;
import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeResponse;
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
public class RegisterTest {
  private FakeRequest request;
  private FakeResponse response;
  private Register register;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  UserValidator userValidator;

  @Mock
  UserRepository userRepository;

  @Before
  public void setUp() {
    request = new FakeRequest();
    response = new FakeResponse();
    register = new Register(userRepository, userValidator);
  }

  @Test
  public void registerNotExistedUser() throws ServletException, IOException {
    final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);
    final ValidationUser validationUser = newValidationUser()
            .name(user.name, "wrongName")
            .nickName(user.nickName, "wrongNickName")
            .email(user.email, "wrongEmail")
            .password(user.password, "wrongPassword")
            .confirmPassword(user.password, "wrongConfirmPassword")
            .city(user.city, "wrongCity")
            .age(String.valueOf(user.age), "wrongAge")
            .build();

    request.setParameters(new HashMap<String, String>() {{
      put("nickName", user.nickName);
      put("password", user.password);
      put("confirmPassword", user.password);
      put("name", user.name);
      put("email", user.email);
      put("city", user.city);
      put("age", String.valueOf(user.age));
    }});

    context.checking(new Expectations() {{
      oneOf(userValidator).validate(validationUser);
      will(returnValue(new HashMap<String, String>()));
      oneOf(userRepository).getUser("ivan@abv.bg");
      will(returnValue(Optional.absent()));
      oneOf(userRepository).register(user);
    }});

    register.doPost(request, response);

    assertThat(response.getRedirectUrl(), is(equalTo("/login")));
  }

  @Test
  public void registeringAlreadyExistingUser() throws IOException, ServletException {
    final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);
    final ValidationUser validationUser = newValidationUser()
            .name(user.name, "wrongName")
            .nickName(user.nickName, "wrongNickName")
            .email(user.email, "wrongEmail")
            .password(user.password, "wrongPassword")
            .confirmPassword(user.password, "wrongConfirmPassword")
            .city(user.city, "wrongCity")
            .age(String.valueOf(user.age), "wrongAge")
            .build();

    request.setParameters(new HashMap<String, String>() {{
      put("nickName", user.nickName);
      put("password", user.password);
      put("confirmPassword", user.password);
      put("name", user.name);
      put("email", user.email);
      put("city", user.city);
      put("age", String.valueOf(user.age));
    }});


    context.checking(new Expectations() {{
      oneOf(userValidator).validate(validationUser);
      will(returnValue(new HashMap<String, String>()));
      oneOf(userRepository).getUser("ivan@abv.bg");
      will(returnValue(Optional.of(user)));
      never(userRepository).register(user);
    }});

    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    response.setOutputStream(out);

    register.doPost(request, response);

    String expected = out.toString();
    assertThat(expected.contains("user already exists"), is(true));
  }
}