package ua.training.servlet_project.controller.command;

import ua.training.servlet_project.model.entity.RequestStatus;
import ua.training.servlet_project.model.exceptions.BookingRequestNotFound;
import ua.training.servlet_project.model.exceptions.OrderNotCreatedException;

import javax.servlet.http.HttpServletRequest;

import static ua.training.servlet_project.model.util.RequestParamsParser.parseBookingRequestStatus;

public class RequestsToOrdersFacade implements Command {
    private static final String BOOKING_REQUEST_NOT_FOUND_EXCEPTION_MESSAGE = "Booking request not found by id";
    private static final String ORDER_NOT_CREATED_EXCEPTION_MESSAGE = "Cannot create order from request with status NEW!";
    private final Command ordersCommand;
    private final Command updateBookingRequestCommand;

    public RequestsToOrdersFacade(Command ordersCommand, Command updateBookingRequestCommand) {
        this.ordersCommand = ordersCommand;
        this.updateBookingRequestCommand = updateBookingRequestCommand;
    }

    @Override
    public String execute(HttpServletRequest request) {
        RequestStatus status = parseBookingRequestStatus(request.getParameter("bookingStatus"),
                new BookingRequestNotFound(BOOKING_REQUEST_NOT_FOUND_EXCEPTION_MESSAGE));
        if (status.equals(RequestStatus.CLOSED)) {
            updateBookingRequestCommand.execute(request);
            return ordersCommand.execute(request);
        } else if (status.equals(RequestStatus.CANCELED)) {
            return updateBookingRequestCommand.execute(request);
        } else {
            throw new OrderNotCreatedException(ORDER_NOT_CREATED_EXCEPTION_MESSAGE);
        }
    }
}
