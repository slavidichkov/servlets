package com.clouway.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class RegularExpressionUserValidator implements UserValidator {
    public Map<String, String> validate(ValidationUser user) {
        Map<String, String> errors = new HashMap<String, String>();
        if (user.name != null && !isOnlyLetters(user.name)) {
            errors.put(user.nameErrorField, "have to be more than 3 symbols and must contain only letters");
        }
        if (user.city != null && !isOnlyLetters(user.city)) {
            errors.put(user.cityErrorField, "have to be betwin 3 and 15 symbols and must contain only letters");
        }
        if (user.nickName != null && !isOnlyLettersAndDigits(user.nickName)) {
            errors.put(user.nickNameErrorField, "have to be betwin 6 and 18 symbols and must contain only digits and letter");
        }
        if (user.password != null && !isOnlyLettersAndDigits(user.password)) {
            errors.put(user.passwordErrorField, "have to be betwin 6 and 18 symbols and must contain only digits and letter");
        }
        if (user.age != null && !isValidAge(user.age)) {
            errors.put(user.ageErrorField, "have to be betwin 18 and 118");
        }
        if (user.email != null && !isCorrectEmail(user.email)) {
            errors.put(user.emailErrorField, "is incorrect");
        }
        if (user.confirmPassword != null && !areSame(user.password, user.confirmPassword)) {
            errors.put(user.confirmPasswordErrorField, "both password are not the same");
        }
        return errors;
    }

    private boolean isOnlyLetters(String value) {
        return value.matches("^[a-zA-Z][a-zA-Z\\s-]+[a-zA-Z]$");
    }

    private boolean isValidAge(String value) {
        return value.matches("^(1[89]|[2-9][0-9]|1[10][0-8])$");
    }

    private boolean isOnlyLettersAndDigits(String value) {
        return value.matches("^([a-zA-Z0-9]){6,18}$");
    }

    private boolean isCorrectEmail(String value) {
        return value.matches("^[A-Za-z0-9-+]*@[A-Za-z0-9-]*(.[A-Za-z]{2,})$");
    }

    private boolean areSame(String a, String b) {
        return a.equals(b);
    }
}
