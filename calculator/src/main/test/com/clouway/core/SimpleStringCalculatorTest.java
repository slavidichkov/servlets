package com.clouway.core;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SimpleStringCalculatorTest{
  private SimpleStringCalculator stringCalculator;

  @Before
  public void setUp(){
    stringCalculator=new SimpleStringCalculator();
  }

  @Test
  public void happyPath() {
      String expected = stringCalculator.eval("34+45/5+5*5-6");
      assertThat("62",is(equalTo(expected)));
  }

  @Test
  public void evaluateWhitSumAndMultiplication() {
      String expected = stringCalculator.eval("3+2*2");
      assertThat("7",is(equalTo(expected)));
  }

  @Test
  public void evaluateDivision() {
      String expected = stringCalculator.eval("35/5");
      assertThat("7",is(equalTo(expected)));
  }

  @Test
  public void evaluateMultiplication() {
      String expected = stringCalculator.eval("5*5");
      assertThat("25",is(equalTo(expected)));
  }
}