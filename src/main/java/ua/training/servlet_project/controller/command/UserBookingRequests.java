package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.BookingRequestDTO;
import ua.training.servlet_project.controller.dto.PageDTO;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.exceptions.BookingRequestNotFound;
import ua.training.servlet_project.model.service.BookingRequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static ua.training.servlet_project.model.util.RequestParamsParser.parseLong;
import static ua.training.servlet_project.model.util.RequestParamsParser.parsePageableFromRequest;

public class UserBookingRequests implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UserBookingRequests.class);
    private static final Pattern ID_PATH_VARIABLE_PATTERN = Pattern.compile("^/app/users/(\\d+)/booking-requests.*(?!a-zA-Z|/)$");
    private static final String USER_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE = "Cannot find user by id";
    private static final String USER_BOOKING_REQUESTS_PAGE = "/JSP/user_booking_requests.jsp";
    private final BookingRequestService bookingRequestService;

    public UserBookingRequests() {
        bookingRequestService = new BookingRequestService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = request.getRequestURI();
        Long userId = parseLong(ID_PATH_VARIABLE_PATTERN.matcher(path).replaceAll("$1"),
                new BookingRequestNotFound(USER_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE));
        Pageable pageable = parsePageableFromRequest(request);
        Page<BookingRequestDTO> requestsPage = bookingRequestService.getAllBookingRequestsByUserId(pageable, userId);

        request.setAttribute("requests", requestsPage.getContent());
        request.setAttribute("page", getBookingRequestsPageDTO(requestsPage, pageable, userId));
        return USER_BOOKING_REQUESTS_PAGE;
    }

    private PageDTO getBookingRequestsPageDTO(Page<BookingRequestDTO> orderPage, Pageable currentPageable, Long userId) {
        LOGGER.info(orderPage.getTotalPages());
        return PageDTO.builder()
                .limit(currentPageable.getPageSize())
                .prevPage(currentPageable.getPageNumber() - 1)
                .nextPage(currentPageable.getPageNumber() + 1)
                .currentPage(currentPageable.getPageNumber() + 1)
                .totalPages(orderPage.getTotalPages())
                .hasPrev(orderPage.hasPrevious())
                .hasNext(orderPage.hasNext())
                .url(String.format("/app/users/%s/booking-requests", userId))
                .build();
    }
}
