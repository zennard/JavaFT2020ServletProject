package ua.training.servlet_project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.BookingRequestCreationDTO;
import ua.training.servlet_project.controller.dto.BookingRequestDTO;
import ua.training.servlet_project.controller.dto.BookingRequestItemDTO;
import ua.training.servlet_project.model.dao.BookingRequestDao;
import ua.training.servlet_project.model.dao.BookingRequestItemDao;
import ua.training.servlet_project.model.dao.DaoFactory;
import ua.training.servlet_project.model.entity.*;
import ua.training.servlet_project.model.exceptions.EmptyBookingRequestException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingRequestService {
    private static final Logger LOGGER = LogManager.getLogger(BookingRequestService.class);
    private static final String BOOKING_REQUEST_NOT_FOUND_EXCEPTION_MESSAGE = "Booking request not found by id: ";
    private final DaoFactory daoFactory;

    public BookingRequestService() {
        daoFactory = DaoFactory.getInstance();
    }

    public Page<BookingRequestDTO> getAllNewBookingRequests(Pageable pageable) {
        Page<BookingRequest> requestsPage;
        try (BookingRequestDao bookingRequestDao = daoFactory.createBookingRequestDao()) {
            requestsPage = bookingRequestDao.findAllByStatus(RequestStatus.NEW, pageable);
        }

        List<BookingRequestDTO> requestsDTO = requestsPage.getContent().stream()
                .map(this::getBookingRequestDTO)
                .collect(Collectors.toList());

        return new Page<>(requestsDTO, requestsPage.getPageable(),
                requestsPage.getTotalPages());
    }

    private BookingRequestDTO getBookingRequestDTO(BookingRequest request) {
        List<BookingRequestItem> items;
        try (BookingRequestItemDao bookingRequestItemDao = daoFactory.createBookingRequestItemDao()) {
            items = bookingRequestItemDao.findAllByRequestId(request.getId());
        }

        if (items.isEmpty()) {
            throw new EmptyBookingRequestException("Cannot create request without request items!");
        }

        List<BookingRequestItemDTO> itemsDTO = items.stream()
                .map(this::getBookingRequestItemDTO)
                .collect(Collectors.toList());

        return BookingRequestDTO.builder()
                .id(request.getId())
                .userId(request.getUser().getId())
                .requestDate(request.getRequestDate())
                .startsAt(request.getStartsAt())
                .endsAt(request.getEndsAt())
                .requestStatus(request.getRequestStatus())
                .requestItems(itemsDTO)
                .build();
    }

    private BookingRequestItemDTO getBookingRequestItemDTO(BookingRequestItem item) {
        return BookingRequestItemDTO.builder()
                .bedsCount(item.getBedsCount())
                .type(item.getType())
                .build();
    }

    public void saveRequest(BookingRequestCreationDTO bookingRequestCreationDTO) {
        LOGGER.info(bookingRequestCreationDTO);
        try (BookingRequestDao bookingRequestDao = daoFactory.createBookingRequestDao()) {
            bookingRequestDao.save(bookingRequestCreationDTO);
        }
    }

    public Optional<BookingRequestDTO> getBookingRequestById(Long id) {
        Optional<BookingRequest> requestOptional;
        try (BookingRequestDao bookingRequestDao = daoFactory.createBookingRequestDao()) {
            requestOptional = bookingRequestDao.findById(id);
        }
        BookingRequest request = requestOptional
                .orElse(null);
        if (request == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(getBookingRequestDTO(request));
    }
}
