package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.BookingRequestDTO;
import ua.training.servlet_project.controller.dto.PageDTO;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.service.BookingRequestService;

import javax.servlet.http.HttpServletRequest;

import static ua.training.servlet_project.model.util.RequestConverter.parseBookingRequestDTO;
import static ua.training.servlet_project.model.util.RequestParamsParser.parsePageableFromRequest;

public class BookingRequests implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BookingRequestCreation.class);
    private static final String BOOKING_REQUEST_CREATION_PAGE_REDIRECT = "redirect:/booking-requests/create";
    private static final String BOOKING_REQUESTS_PAGE = "/JSP/booking_requests.jsp";
    private final BookingRequestService bookingRequestService;

    public BookingRequests() {
        bookingRequestService = new BookingRequestService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            Pageable pageable = parsePageableFromRequest(request);
            Page<BookingRequestDTO> requestsPage = bookingRequestService.getAllNewBookingRequests(pageable);
            request.setAttribute("requests", requestsPage.getContent());
            request.setAttribute("page", getRequestsPageDTO(requestsPage, pageable));
            return BOOKING_REQUESTS_PAGE;
        }
        //@TODO process in jsp
        request.setAttribute("successfulCreation", true);
        return doPost(request);
    }

    private PageDTO getRequestsPageDTO(Page<BookingRequestDTO> requestsPage, Pageable currentPageable) {
        return PageDTO.builder()
                .limit(currentPageable.getPageSize())
                .prevPage(currentPageable.getPageNumber() - 1)
                .nextPage(currentPageable.getPageNumber() + 1)
                .currentPage(currentPageable.getPageNumber() + 1)
                .totalPages(requestsPage.getTotalPages())
                .hasPrev(requestsPage.hasPrevious())
                .hasNext(requestsPage.hasNext())
                .url("/app/booking-requests")
                .build();
    }

    private String doPost(HttpServletRequest request) {
        bookingRequestService.saveRequest(parseBookingRequestDTO(request));

        return BOOKING_REQUEST_CREATION_PAGE_REDIRECT;
    }
}
