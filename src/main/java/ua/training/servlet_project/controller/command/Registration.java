package ua.training.servlet_project.controller.command;

import ua.training.servlet_project.controller.dto.UserRegistrationDTO;
import ua.training.servlet_project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class Registration implements Command {
    private static final String REGISTRATION_PAGE = "/JSP/registration.jsp";
    private static final String INDEX_PAGE_REDIRECT = "redirect:/app";
    private static final int MINIMAL_FIRST_NAME_SIZE = 2;
    private static final int MAXIMUM_FIRST_NAME_SIZE = 30;
    private static final int MINIMAL_LAST_NAME_SIZE = 2;
    private static final int MAXIMUM_LAST_NAME_SIZE = 30;
    private static final int MINIMAL_PASSWORD_SIZE = 8;
    private static final int MAXIMUM_PASSWORD_SIZE = 40;
    private static final Pattern emailPattern = Pattern.compile(".+@.+\\..+");
    private final UserService userService;

    public Registration() {
        userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return REGISTRATION_PAGE;
        }

        UserRegistrationDTO user = UserRegistrationDTO.builder()
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();

        //@TODO check password and repeated password on server side
        if (!isValid(request, user)) {
            request.setAttribute("prevFormData", user);
            return REGISTRATION_PAGE;
        }

        userService.saveNewUser(user);

        return INDEX_PAGE_REDIRECT;
    }

    private boolean isValid(HttpServletRequest request, UserRegistrationDTO user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();

        boolean isFirstNameValid = firstName != null && firstName.length() >= MINIMAL_FIRST_NAME_SIZE
                && firstName.length() <= MAXIMUM_FIRST_NAME_SIZE;
        boolean isLastNameValid = lastName != null && lastName.length() >= MINIMAL_LAST_NAME_SIZE
                && lastName.length() <= MAXIMUM_LAST_NAME_SIZE;
        boolean isPasswordValid = password != null && password.length() >= MINIMAL_PASSWORD_SIZE
                && password.length() <= MAXIMUM_PASSWORD_SIZE;
        boolean isEmailValid = email != null && emailPattern.matcher(email).matches();

        if (!isFirstNameValid) {
            request.setAttribute("firstNameError", true);
        }
        if (!isLastNameValid) {
            request.setAttribute("lastNameError", true);
        }
        if (!isEmailValid) {
            request.setAttribute("emailError", true);
        }
        if (!isPasswordValid) {
            request.setAttribute("passwordError", true);
        }

        return isFirstNameValid && isLastNameValid && isPasswordValid && isEmailValid;
    }
}
