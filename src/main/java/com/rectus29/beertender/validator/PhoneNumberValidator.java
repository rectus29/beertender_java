package com.rectus29.beertender.validator;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 09/01/13 16:29 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements IValidator<String> {

    private static final Pattern NUMBER = Pattern.compile("[0-9]{10}");

    public void validate(IValidatable<String> validatable) {
        String password = validatable.getValue();
        password = password.replaceAll(" ","");
        if (!NUMBER.matcher(password).find()) {
            error(validatable, "no-digit");
        }
    }

    private void error(IValidatable<String> validatable, String errorKey) {
        ValidationError error = new ValidationError();
        error.addKey(getClass().getSimpleName() + "." + errorKey);
        validatable.error(error);
    }
}
