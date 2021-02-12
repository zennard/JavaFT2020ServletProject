package ua.training.servlet_project.model.exceptions;

public class OrderStatusParseException extends RuntimeException {
    public OrderStatusParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
