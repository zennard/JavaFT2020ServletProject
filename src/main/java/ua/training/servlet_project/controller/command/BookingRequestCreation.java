package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class BookingRequestCreation implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BookingRequestCreation.class);
    private static final String BOOKING_REQUEST_CREATION_PAGE = "/JSP/booking_request_creation.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        return BOOKING_REQUEST_CREATION_PAGE;
    }
}
