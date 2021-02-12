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

        //@TODO change on method calls

        if (isFirstNameNotValid(firstName)) {
            validationResult.addErrorValue("firstNameError");
        }
        if (isLastNameNotValid(lastName)) {
            validationResult.addErrorValue("lastNameError");
        }
        if (isEmailNotValid(email)) {
            validationResult.addErrorValue("emailError");
        }
        if (isPasswordNotValid(password)) {
            validationResult.addErrorValue("passwordError");
        }

        return validationResult;
    }

    private static boolean isEmailNotValid(String email) {
        return email == null || !emailPattern.matcher(email).matches();
    }

    private static boolean isPasswordNotValid(String password) {
        return password == null || password.length() < MINIMAL_PASSWORD_SIZE
                || password.length() > MAXIMUM_PASSWORD_SIZE;
    }

    private static boolean isLastNameNotValid(String lastName) {
        return lastName == null || lastName.length() < MINIMAL_LAST_NAME_SIZE
                || lastName.length() > MAXIMUM_LAST_NAME_SIZE;
    }

    private static boolean isFirstNameNotValid(String firstName) {
        return firstName == null || firstName.length() < MINIMAL_FIRST_NAME_SIZE
                || firstName.length() > MAXIMUM_FIRST_NAME_SIZE;
    }


}
