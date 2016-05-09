package com.clouway.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SimpleStringCalculator implements StringCalculator {
  public String eval(String input) {
    List<String> numbers = new ArrayList(Arrays.asList(input.split("[-+*/]")));
    List<String> operators = new ArrayList(Arrays.asList(input.split("[0-9]")));
    operators.removeAll(Collections.singleton(""));
    return calculate(numbers,operators);
  }

  private String calculate(List<String> numbers, List<String> operators) {
    int i=0;
    int result=new Integer(numbers.get(0));
    while ( i < operators.size()) {
      int temp=0;
      if (i + 1 != operators.size()) {
        if ("*".equals(operators.get(i + 1))) {
          temp = new Integer(numbers.get(i + 1)) * new Integer(numbers.get(i + 2));
          result = calculateValues(result, temp, operators.get(i));
          i += 2;
          continue;
        } else if ("/".equals(operators.get(i + 1))) {
          temp = new Integer(numbers.get(i + 1)) / new Integer(numbers.get(i + 2));
          result = calculateValues(result, temp, operators.get(i));
          i += 2;
          continue;
        }
      }
      result = calculateValues(result,new Integer(numbers.get(i + 1)),operators.get(i));
      i++;

    }
    return String.valueOf(result);
  }

  private int calculateValues(int firsValue, int secondValue, String operator) {
    int result=0;
    if ("+".equals(operator)) {
      result = firsValue + secondValue;
    }
    if ("-".equals(operator)) {
      result = firsValue - secondValue;
    }
    if ("*".equals(operator)) {
      result = firsValue * secondValue;
    }
    if ("/".equals(operator)) {
      result = firsValue / secondValue;
    }
    return result;
  }
}
