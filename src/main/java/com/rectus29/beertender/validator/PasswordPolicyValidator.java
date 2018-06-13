package com.rectus29.beertender.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 29/04/11
 * Time: 09:53
 * To change this template use File | Settings | File Templates.
 */
public class PasswordPolicyValidator implements IValidator<String> {
    private static final Pattern UPPER = Pattern.compile("[A-Z]");
    private static final Pattern LOWER = Pattern.compile("[a-z]");
    private static final Pattern NUMBER = Pattern.compile("[0-9]");

    public void validate(IValidatable<String> validatable) {
        final String password = validatable.getValue();

        if (!NUMBER.matcher(password).find()) {
            error(validatable, "no-digit");
        }

        if (!LOWER.matcher(password).find()) {
            error(validatable, "no-lower");
        }

        if (!UPPER.matcher(password).find()) {
            error(validatable, "no-upper");
        }
    }

    private void error(IValidatable<String> validatable, String errorKey) {
        ValidationError error = new ValidationError();
        error.setMessage(getClass().getSimpleName() + "." + errorKey);
        validatable.error(error);
    }

}
