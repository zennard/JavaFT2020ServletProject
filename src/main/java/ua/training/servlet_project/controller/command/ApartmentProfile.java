package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.entity.Apartment;
import ua.training.servlet_project.model.entity.ApartmentTimetable;
import ua.training.servlet_project.model.exceptions.ApartmentNotFoundException;
import ua.training.servlet_project.model.exceptions.IllegalDateException;
import ua.training.servlet_project.model.exceptions.WrongTimetableIdException;
import ua.training.servlet_project.model.service.ApartmentService;
import ua.training.servlet_project.model.util.PropertiesLoader;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;

public class ApartmentProfile implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ApartmentProfile.class);
    public static final Pattern ID_PATH_VARIABLE_PATTERN = Pattern.compile("^/app/apartments/(\\d+).*(?!a-zA-Z|/)$");
    private static final String APARTMENT_NOT_FOUND_EXCEPTION_MESSAGE = "Apartment not found by id: ";
    private static final String ILLEGAL_DATE_EXCEPTION_MESSAGE = "Illegal date requested in apartment get";
    private static final String APARTMENT_PAGE = "/JSP/apartment.jsp";
    private static final int SETTLEMENT_MINUTES = 0;
    //@TODO move to some constants class
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
        //@TODO apartment page controller
        Long id = null;
        Long timeSlotId = null;
        LocalDate startsAt = null;
        LocalDate endsAt = null;
        String lang = (String) request.getSession().getAttribute("lang");

        LOGGER.info("lang: " + lang);

        String path = request.getRequestURI();

        String idValue = ID_PATH_VARIABLE_PATTERN.matcher(path).replaceAll("$1");
        try {
            id = Long.valueOf(idValue);
        } catch (NumberFormatException ex) {
            LOGGER.error("Apartment id parse exception: ", ex);
            throw new ApartmentNotFoundException(APARTMENT_NOT_FOUND_EXCEPTION_MESSAGE + idValue);
        }

        String timeSlotIdValue = request.getParameter("slotId");
        try {
            timeSlotId = Long.valueOf(timeSlotIdValue);
        } catch (NumberFormatException ex) {
            LOGGER.error("Time slot id parse exception: ", ex);
        }

        try {
            startsAt = LocalDate.parse(request.getParameter("startsAt"));
            endsAt = LocalDate.parse(request.getParameter("endsAt"));

        } catch (DateTimeParseException | NullPointerException ex) {
            LOGGER.error("date parse exception: ", ex);
            throw new IllegalDateException(ILLEGAL_DATE_EXCEPTION_MESSAGE);
        }


        Apartment apartment = apartmentService.getApartmentByIdAndDate(id, startsAt, endsAt, lang);

        LOGGER.info("{}", apartment);
        LOGGER.info("{}", apartment.getSchedule());

        ApartmentTimetable schedule = Optional.ofNullable(timeSlotId)
                .map(tId -> {
                    LOGGER.info("here");
                    return apartment
                            .getSchedule()
                            .stream()
                            .filter(t -> tId.equals(t.getId()))
                            .findFirst()
                            .orElseThrow(() -> new WrongTimetableIdException("Cannot find record with this id"));
                })
                .orElseGet(() -> apartment.getSchedule().get(0));

        request.setAttribute("apartment", apartment);
        //@TODO change on single parameter
        request.setAttribute("apartmentIds", Collections.singletonList(apartment.getId()));
        request.setAttribute("schedule", schedule);
        request.setAttribute("userStartsAt", LocalDateTime.of(startsAt,
                LocalTime.of(checkInHours, SETTLEMENT_MINUTES)));
        request.setAttribute("userEndsAt", LocalDateTime.of(endsAt,
                LocalTime.of(checkOutHours, SETTLEMENT_MINUTES)));

//        Optional.ofNullable(authentication)
//                .ifPresent(auth -> {
//                    UserPrincipal user = (UserPrincipal) auth.getPrincipal();
//                    model.addAttribute("userId", user.getUserId());
//                });

        LOGGER.info(path);

        return APARTMENT_PAGE;
    }
}
