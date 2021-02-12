package ua.training.servlet_project.controller.command;

import ua.training.servlet_project.controller.dto.BookingRequestUpdateDTO;
import ua.training.servlet_project.model.entity.RequestStatus;
import ua.training.servlet_project.model.exceptions.BookingRequestNotFound;
import ua.training.servlet_project.model.service.BookingRequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static ua.training.servlet_project.model.util.RequestParamsParser.parseBookingRequestStatus;
import static ua.training.servlet_project.model.util.RequestParamsParser.parseLong;

public class UpdateBookingRequest implements Command {
    private static final Pattern ID_PATH_VARIABLE_PATTERN = Pattern.compile("^/app/booking-requests/update/(\\d+).*(?!a-zA-Z|/)$");
    private static final String BOOKING_REQUEST_NOT_FOUND_EXCEPTION_MESSAGE = "Booking request not found by id";
    private static final String BOOKING_REQUESTS_PAGE_REDIRECT = "redirect:/booking-requests";
    private final BookingRequestService bookingRequestService;

    public UpdateBookingRequest() {
        bookingRequestService = new BookingRequestService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = request.getRequestURI();
        Long id = parseLong(ID_PATH_VARIABLE_PATTERN.matcher(path).replaceAll("$1"),
                new BookingRequestNotFound(BOOKING_REQUEST_NOT_FOUND_EXCEPTION_MESSAGE));
        RequestStatus status = parseBookingRequestStatus(request.getParameter("bookingStatus"),
                new BookingRequestNotFound(BOOKING_REQUEST_NOT_FOUND_EXCEPTION_MESSAGE));
        bookingRequestService.updateRequestStatus(
                BookingRequestUpdateDTO.builder()
                        .id(id)
                        .status(status)
                        .build()
        );
        if (status.equals(RequestStatus.CLOSED)) {
            
        }
        return BOOKING_REQUESTS_PAGE_REDIRECT;
    }
}
