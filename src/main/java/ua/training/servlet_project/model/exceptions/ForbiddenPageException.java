package ua.training.servlet_project.model.exceptions;

public class ForbiddenPageException extends RuntimeException {
    public ForbiddenPageException(String message) {
        super(message);
    }
}
