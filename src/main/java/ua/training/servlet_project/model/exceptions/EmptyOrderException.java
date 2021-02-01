package ua.training.servlet_project.model.exceptions;

public class EmptyOrderException extends RuntimeException {
    public EmptyOrderException(String message) {
        super(message);
    }
}
