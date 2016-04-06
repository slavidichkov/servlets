package com.clouway.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SimpleStringCalculator implements StringCalculator {
    public String eval(String input){
        List<Long> numbers = new ArrayList();
        List<String> operators = new ArrayList();
        String number="";
        for (int i=0;i<=input.length()-1;i++) {
            String charAt = String.valueOf(input.charAt(i));
            if (charAt.matches("[0-9]")) {
                number += charAt;
            } else {
                numbers.add(new Long(number));
                operators.add(charAt);
                number = "";
            }
        }

        numbers.add(new Long(number));
        Long result = numbers.get(0);

        for (int i=0;i<=operators.size()-1;i++){
            if ("+".equals(operators.get(i))){
                result+=numbers.get(i+1);
            }
            if ("-".equals(operators.get(i))){
                result-=numbers.get(i+1);
            }
            if ("*".equals(operators.get(i))){
                result*=numbers.get(i+1);
            }
            if ("/".equals(operators.get(i))){
                result/=numbers.get(i+1);
            }
        }
        return String.valueOf(result);
    }
}
