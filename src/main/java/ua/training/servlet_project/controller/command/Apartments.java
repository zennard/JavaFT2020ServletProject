package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.ApartmentPageContextDTO;
import ua.training.servlet_project.controller.dto.DateDTO;
import ua.training.servlet_project.controller.dto.PageDTO;
import ua.training.servlet_project.model.entity.Apartment;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.entity.SortType;
import ua.training.servlet_project.model.service.ApartmentService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Apartments implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Apartments.class);
    private static final String APARTMENTS_PAGE = "/JSP/apartments.jsp";
    private static final int DEFAULT_DAYS_OFFSET = 3;
    //@TODO get from properties
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 2;
    private static final Integer MIN_PAGE_SIZE = 0;
    private static final Integer MAX_PAGE_SIZE = 4;
    private static final String WRONG_PAGE_SIZE_EXCEPTION_MESSAGE = "Wrong page size value: ";
    private final ApartmentService apartmentService;

    public Apartments() {
        apartmentService = new ApartmentService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer page = null;
        Integer size = null;
        LocalDate startsAt = null;
        LocalDate endsAt = null;
        SortType sortBy = null;

        try {
            sortBy = SortType.valueOf(request.getParameter("sort").toUpperCase());
        } catch (NullPointerException | IllegalArgumentException ex) {
            LOGGER.error("sort by type exception: ", ex);
            sortBy = SortType.ID;
        }

        try {
            page = Integer.valueOf(request.getParameter("page"));
            size = Integer.valueOf(request.getParameter("size"));

            if (size < MIN_PAGE_SIZE || size > MAX_PAGE_SIZE) {
                throw new NumberFormatException(WRONG_PAGE_SIZE_EXCEPTION_MESSAGE + size);
            }
        } catch (NumberFormatException nex) {
            LOGGER.error(nex);
            page = DEFAULT_PAGE_NUMBER;
            size = DEFAULT_PAGE_SIZE;
        }

        try {
            startsAt = LocalDate.parse(request.getParameter("startsAt"));
            endsAt = LocalDate.parse(request.getParameter("endsAt"));

        } catch (DateTimeParseException | NullPointerException ex) {
            LOGGER.error("date parse exception: ", ex);
            startsAt = LocalDate.now().minusDays(DEFAULT_DAYS_OFFSET);
            endsAt = LocalDate.now().plusDays(DEFAULT_DAYS_OFFSET);
        }

        Pageable pageable = new Pageable(page, size, sortBy);

        LOGGER.info("{}", startsAt);
        LOGGER.info("{}", endsAt);
        LOGGER.info("{}", sortBy);

        Page<Apartment> apartmentPage = apartmentService.getAllAvailableApartmentsByDate(pageable, startsAt, endsAt);
        Pageable currentPageable = apartmentPage.getPageable();

        request.setAttribute("pageContextVar",
                ApartmentPageContextDTO.builder()
                        .apartments(apartmentPage.getContent())
                        .page(getPageDTO(apartmentPage, currentPageable))
                        .date(getDateDTO(startsAt, endsAt))
                        .build()
        );
        LOGGER.info(getPageDTO(apartmentPage, currentPageable));
//        Optional.ofNullable(authentication)
//                .ifPresent(auth -> {
//                    UserPrincipal user = (UserPrincipal) auth.getPrincipal();
//                    model.addAttribute("userId", user.getUserId());
//                });

        return APARTMENTS_PAGE;
    }

    private DateDTO getDateDTO(LocalDate startsAt, LocalDate endsAt) {
        return DateDTO.builder()
                .prevYear(LocalDateTime.now().getYear() - 1)
                .nextYear(LocalDateTime.now().getYear() + 1)
                .checkIn(startsAt)
                .checkOut(endsAt)
                .build();
    }

    private PageDTO getPageDTO(Page<Apartment> apartmentPage, Pageable currentPageable) {
        return PageDTO.builder()
                .limit(currentPageable.getPageSize())
                .prevPage(currentPageable.getPageNumber() - 1)
                .nextPage(currentPageable.getPageNumber() + 1)
                .currentPage(currentPageable.getPageNumber() + 1)
                .totalPages(apartmentPage.getTotalPages())
                .hasPrev(apartmentPage.hasPrevious())
                .hasNext(apartmentPage.hasNext())
                .url("/app/apartments")
                .build();
    }
}
