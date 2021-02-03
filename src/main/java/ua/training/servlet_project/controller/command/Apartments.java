package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.ApartmentPageContextDTO;
import ua.training.servlet_project.controller.dto.DateDTO;
import ua.training.servlet_project.controller.dto.PageDTO;
import ua.training.servlet_project.controller.dto.VacationDateDTO;
import ua.training.servlet_project.model.entity.Apartment;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.service.ApartmentService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static ua.training.servlet_project.model.util.RequestParamsParser.parsePageableFromRequest;
import static ua.training.servlet_project.model.util.RequestParamsParser.parseVacationDateFromRequest;

public class Apartments implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Apartments.class);
    private static final String APARTMENTS_PAGE = "/JSP/apartments.jsp";
    private final ApartmentService apartmentService;

    public Apartments() {
        apartmentService = new ApartmentService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        VacationDateDTO vacationDateDTO = parseVacationDateFromRequest(request);
        Pageable pageable = parsePageableFromRequest(request);

        Page<Apartment> apartmentPage = apartmentService.getAllAvailableApartmentsByDate(pageable, vacationDateDTO);
        Pageable currentPageable = apartmentPage.getPageable();

        request.setAttribute("pageContextVar",
                ApartmentPageContextDTO.builder()
                        .apartments(apartmentPage.getContent())
                        .page(getPageDTO(apartmentPage, currentPageable))
                        .date(getDateDTO(vacationDateDTO))
                        .build()
        );

        return APARTMENTS_PAGE;
    }

    private DateDTO getDateDTO(VacationDateDTO vacationDateDTO) {
        return DateDTO.builder()
                .prevYear(LocalDateTime.now().getYear() - 1)
                .nextYear(LocalDateTime.now().getYear() + 1)
                .checkIn(vacationDateDTO.getStartsAt())
                .checkOut(vacationDateDTO.getEndsAt())
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
