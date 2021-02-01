package ua.training.servlet_project.model.exceptions;

public class IllegalEmailException extends RuntimeException {
    public IllegalEmailException(Throwable cause) {
        super(cause);
    }
}
