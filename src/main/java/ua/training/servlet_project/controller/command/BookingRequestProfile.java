package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.ApartmentCriteriaDTO;
import ua.training.servlet_project.controller.dto.BookingRequestDTO;
import ua.training.servlet_project.controller.dto.BookingRequestItemDTO;
import ua.training.servlet_project.controller.dto.VacationDateDTO;
import ua.training.servlet_project.model.entity.*;
import ua.training.servlet_project.model.exceptions.BookingRequestNotFound;
import ua.training.servlet_project.model.service.ApartmentService;
import ua.training.servlet_project.model.service.BookingRequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static ua.training.servlet_project.model.util.RequestParamsParser.parseLong;
import static ua.training.servlet_project.model.util.RequestParamsParser.parsePageableFromRequest;

public class BookingRequestProfile implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BookingRequestProfile.class);
    private static final Pattern ID_PATH_VARIABLE_PATTERN = Pattern.compile("^/app/booking-requests/(\\d+).*(?!a-zA-Z|/)$");
    private static final String BOOKING_REQUEST_NOT_FOUND_EXCEPTION_MESSAGE = "Booking request not found by id";
    private static final String BOOKING_REQUEST_PAGE = "/JSP/booking_request.jsp";
    private final BookingRequestService bookingRequestService;
    private final ApartmentService apartmentService;

    public BookingRequestProfile() {
        bookingRequestService = new BookingRequestService();
        apartmentService = new ApartmentService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = request.getRequestURI();
        Long id = parseLong(ID_PATH_VARIABLE_PATTERN.matcher(path).replaceAll("$1"),
                new BookingRequestNotFound(BOOKING_REQUEST_NOT_FOUND_EXCEPTION_MESSAGE));
        BookingRequestDTO bookingRequest = bookingRequestService.getBookingRequestById(id)
                .orElseThrow(() -> new BookingRequestNotFound(BOOKING_REQUEST_NOT_FOUND_EXCEPTION_MESSAGE));
        Pageable pageable = parsePageableFromRequest(request);
        List<RoomType> types = getUniqueTypes(bookingRequest.getRequestItems());
        Page<Apartment> apartmentPage = apartmentService.getAllAvailableApartmentsByDate(pageable,
                new VacationDateDTO(bookingRequest.getStartsAt().toLocalDate(),
                        bookingRequest.getEndsAt().toLocalDate()),
                new ApartmentCriteriaDTO(types, RoomStatus.FREE));

        request.setAttribute("bookingRequest", bookingRequest);
        request.setAttribute("apartments", apartmentPage.getContent());
        request.setAttribute("page", Apartments.getPageDTO(apartmentPage, path));
        return BOOKING_REQUEST_PAGE;
    }

    private List<RoomType> getUniqueTypes(List<BookingRequestItemDTO> items) {
        Set<RoomType> roomSet = new HashSet<>();
        items.forEach(item -> roomSet.add(item.getType()));
        return new ArrayList<>(roomSet);
    }
}
