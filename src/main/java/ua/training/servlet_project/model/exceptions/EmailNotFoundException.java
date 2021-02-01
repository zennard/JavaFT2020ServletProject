package ua.training.servlet_project.model.exceptions;


public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
