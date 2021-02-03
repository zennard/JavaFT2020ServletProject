package ua.training.servlet_project.model.util;

import ua.training.servlet_project.controller.dto.UserRegistrationDTO;

import javax.servlet.http.HttpServletRequest;

public class RequestConverter {
    private RequestConverter() {
    }

    public static UserRegistrationDTO parseUserRegistrationDTO(HttpServletRequest request) {
        return UserRegistrationDTO.builder()
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();
    }
}
