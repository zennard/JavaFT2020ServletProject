package ua.training.servlet_project.model.exceptions;

public class EmptyBookingRequestException extends RuntimeException {
    public EmptyBookingRequestException(String message) {
        super(message);
    }
}
