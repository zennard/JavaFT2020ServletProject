package ua.training.servlet_project.model.exceptions;

public class BookingRequestNotFound extends RuntimeException {
    public BookingRequestNotFound(String message) {
        super(message);
    }
}
