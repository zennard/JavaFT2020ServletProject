package ua.training.servlet_project.model.exceptions;

public class BookingRequestCreationException extends RuntimeException {
    public BookingRequestCreationException(String message) {
        super(message);
    }
}
