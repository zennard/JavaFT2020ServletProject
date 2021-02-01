package ua.training.servlet_project.model.exceptions;

public class WrongTimetableIdException extends IllegalArgumentException {
    public WrongTimetableIdException(String message) {
        super(message);
    }
}
