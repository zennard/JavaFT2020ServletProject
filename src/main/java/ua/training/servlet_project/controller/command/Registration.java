package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.UserRegistrationDTO;
import ua.training.servlet_project.model.entity.ValidationResult;
import ua.training.servlet_project.model.service.UserService;
import ua.training.servlet_project.model.util.RequestConverter;
import ua.training.servlet_project.model.util.Validator;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Registration.class);
    private static final String REGISTRATION_PAGE = "/JSP/registration.jsp";
    private static final String INDEX_PAGE_REDIRECT = "redirect:/app";
    private final UserService userService;

    public Registration() {
        userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return REGISTRATION_PAGE;
        }

        //@TODO check password and repeated password on server side
        UserRegistrationDTO user = RequestConverter.parseUserRegistrationDTO(request);
        ValidationResult validationResult = Validator.validate(user);
        if (!validationResult.isValid()) {
            request.setAttribute("prevFormData", user);
            request.setAttribute("firstNameError", validationResult.getErrors().contains("firstNameError"));
            request.setAttribute("lastNameError", validationResult.getErrors().contains("lastNameError"));
            request.setAttribute("emailError", validationResult.getErrors().contains("emailError"));
            request.setAttribute("passwordError", validationResult.getErrors().contains("passwordError"));
            return REGISTRATION_PAGE;
        }

        userService.saveNewUser(user);

        return INDEX_PAGE_REDIRECT;
    }
}
