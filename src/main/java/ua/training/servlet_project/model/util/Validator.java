package ua.training.servlet_project.model.util;

import ua.training.servlet_project.controller.dto.UserRegistrationDTO;
import ua.training.servlet_project.model.entity.ValidationResult;

import java.util.regex.Pattern;

public class Validator {
    private static final int MINIMAL_FIRST_NAME_SIZE = 2;
    private static final int MAXIMUM_FIRST_NAME_SIZE = 30;
    private static final int MINIMAL_LAST_NAME_SIZE = 2;
    private static final int MAXIMUM_LAST_NAME_SIZE = 30;
    private static final int MINIMAL_PASSWORD_SIZE = 8;
    private static final int MAXIMUM_PASSWORD_SIZE = 40;
    private static final Pattern emailPattern = Pattern.compile(".+@.+\\..+");

    private Validator() {
    }

    public static ValidationResult validate(UserRegistrationDTO userDto) {
        ValidationResult validationResult = new ValidationResult();
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        boolean isFirstNameValid = firstName != null && firstName.length() >= MINIMAL_FIRST_NAME_SIZE
                && firstName.length() <= MAXIMUM_FIRST_NAME_SIZE;
        boolean isLastNameValid = lastName != null && lastName.length() >= MINIMAL_LAST_NAME_SIZE
                && lastName.length() <= MAXIMUM_LAST_NAME_SIZE;
        boolean isPasswordValid = password != null && password.length() >= MINIMAL_PASSWORD_SIZE
                && password.length() <= MAXIMUM_PASSWORD_SIZE;
        boolean isEmailValid = email != null && emailPattern.matcher(email).matches();

        if (!isFirstNameValid) {
            validationResult.addErrorValue("firstNameError");
        }
        if (!isLastNameValid) {
            validationResult.addErrorValue("lastNameError");
        }
        if (!isEmailValid) {
            validationResult.addErrorValue("emailError");
        }
        if (!isPasswordValid) {
            validationResult.addErrorValue("passwordError");
        }

        return validationResult;
    }

}
