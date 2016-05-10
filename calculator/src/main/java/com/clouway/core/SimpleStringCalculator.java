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
    operators.removeAll(Collections.singleton("."));
    return calculate(numbers, operators);
  }

  private String calculate(List<String> numbers, List<String> operators) {
    int i=0;
    double result = Double.valueOf(numbers.get(i));
    while ( i < operators.size()) {
      if ("*".equals(operators.get(i))) {
        Double product = new Double(numbers.get(i)) * new Double(numbers.get(i + 1));
        numbers.remove(i);
        numbers.set(i, product.toString());
        operators.remove(i);
        result = product;

        if (!operators.contains("/") && !operators.contains("*")) {
          result = Double.valueOf(numbers.get(0));
          i = 0;
        }
        continue;
      }
      if ("/".equals(operators.get(i))) {
        Double product = new Double(numbers.get(i)) / new Double(numbers.get(i + 1));
        numbers.remove(i);
        numbers.set(i, product.toString());
        operators.remove(i);
        result = product;

        if (!operators.contains("/") && !operators.contains("*")) {
          result = Double.valueOf(numbers.get(0));
          i = 0;
        }
        continue;
      }
      if (!operators.contains("/") && !operators.contains("*")) {
        result = calculateValues(result, Double.valueOf(numbers.get(i+1)), operators.get(i));
      }
      i++;
    }
    return String.valueOf(result);
  }

  private double calculateValues(double firsValue, double secondValue, String operator) {
    double result=0;
    if ("+".equals(operator)) {
      result = firsValue + secondValue;
    }
    if ("-".equals(operator)) {
      result = firsValue - secondValue;
    }
    return result;
  }
}
