package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.VacationDateDTO;
import ua.training.servlet_project.model.entity.Apartment;
import ua.training.servlet_project.model.entity.ApartmentTimetable;
import ua.training.servlet_project.model.exceptions.ApartmentNotFoundException;
import ua.training.servlet_project.model.service.ApartmentService;
import ua.training.servlet_project.model.util.PropertiesLoader;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;

import static ua.training.servlet_project.model.util.RequestParamsParser.parseLong;
import static ua.training.servlet_project.model.util.RequestParamsParser.parseVacationDateFromRequest;

public class ApartmentProfile implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ApartmentProfile.class);
    public static final Pattern ID_PATH_VARIABLE_PATTERN = Pattern.compile("^/app/apartments/(\\d+).*(?!a-zA-Z|/)$");
    private static final String APARTMENT_NOT_FOUND_EXCEPTION_MESSAGE = "Apartment not found by id";
    private static final String APARTMENT_PAGE = "/JSP/apartment.jsp";
    private static final int SETTLEMENT_MINUTES = 0;
    private final Integer checkInHours;
    private final Integer checkOutHours;
    private ApartmentService apartmentService;

    public ApartmentProfile() {
        Properties properties = PropertiesLoader.getProperties("application.properties");
        checkInHours = Integer.valueOf(properties.getProperty("apartment.check.in.time"));
        checkOutHours = Integer.valueOf(properties.getProperty("apartment.check.out.time"));
        apartmentService = new ApartmentService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = request.getRequestURI();
        Long id = parseLong(ID_PATH_VARIABLE_PATTERN.matcher(path).replaceAll("$1"),
                new ApartmentNotFoundException(APARTMENT_NOT_FOUND_EXCEPTION_MESSAGE));
        LOGGER.info(id);
        Long timeSlotId = parseLong(request.getParameter("slotId"), -1L);
        //@TODO REMOVE BAD CODE, QUICK FIX
        String lang = (String) request.getSession().getAttribute("lang");
        if (lang == null) {
            lang = "en";
        }
        VacationDateDTO vacationDateDTO = parseVacationDateFromRequest(request);

        LOGGER.info("lang: " + lang);

        Apartment apartment = apartmentService.getApartmentByIdAndDate(id, vacationDateDTO, lang);

        LOGGER.info("{}", apartment);
        LOGGER.info("{}", apartment.getSchedule());

        ApartmentTimetable schedule = Optional.ofNullable(timeSlotId)
                .flatMap(tId -> apartment
                        .getSchedule()
                        .stream()
                        .filter(t -> tId.equals(t.getId()))
                        .findFirst())
                .orElseGet(() -> apartment.getSchedule().get(0));

        request.setAttribute("apartment", apartment);
        //@TODO change on single parameter
        request.setAttribute("apartmentIds", Collections.singletonList(apartment.getId()));
        request.setAttribute("schedule", schedule);
        request.setAttribute("userStartsAt", LocalDateTime.of(vacationDateDTO.getStartsAt(),
                LocalTime.of(checkInHours, SETTLEMENT_MINUTES)));
        request.setAttribute("userEndsAt", LocalDateTime.of(vacationDateDTO.getEndsAt(),
                LocalTime.of(checkOutHours, SETTLEMENT_MINUTES)));

        LOGGER.info(path);

        return APARTMENT_PAGE;
    }
}
