package ua.training.servlet_project.model.exceptions;

public class DescriptionNotFoundException extends RuntimeException {
    public DescriptionNotFoundException(String message) {
        super(message);
    }
}
