package ua.training.servlet_project.model.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.VacationDateDTO;
import ua.training.servlet_project.model.entity.OrderStatus;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.entity.SortType;
import ua.training.servlet_project.model.exceptions.OrderStatusParseException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RequestParamsParser {
    private static final Logger LOGGER = LogManager.getLogger(RequestParamsParser.class);
    private static final int DEFAULT_DAYS_OFFSET = 3;
    public static final String LONG_TYPE_SUFFIX = "L";
    //@TODO get from properties
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 2;
    private static final Integer MIN_PAGE_SIZE = 0;
    private static final Integer MAX_PAGE_SIZE = 4;
    private static final String WRONG_PAGE_SIZE_EXCEPTION_MESSAGE = "Wrong page size value: ";

    private RequestParamsParser() {
    }

    public static LocalDate parseDate(String dateValue, LocalDate defaultValue) {
        try {
            return LocalDate.parse(dateValue);
        } catch (DateTimeParseException | NullPointerException ex) {
            LOGGER.error("date parse exception: ", ex);
            return defaultValue;
        }
    }

    public static Integer parseInteger(String integerValue, Integer defaultValue) {
        try {
            return Integer.valueOf(integerValue);
        } catch (NumberFormatException nex) {
            LOGGER.error(nex);
            return defaultValue;
        }
    }

    public static Long parseLong(String longValue, Long defaultValue) {
        try {
            return Long.valueOf(longValue);
        } catch (NumberFormatException nex) {
            LOGGER.error(nex);
            return defaultValue;
        }
    }

    public static Long parseLong(String longValue, RuntimeException ex) {
        try {
            LOGGER.info(longValue);
            return Long.valueOf(longValue);
        } catch (RuntimeException nex) {
            LOGGER.error(ex);
            throw ex;
        }
    }

    public static SortType parseSortType(String sortValue, SortType defaultValue) {
        try {
            return SortType.valueOf(sortValue.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException ex) {
            LOGGER.error("sort by type parse exception: ", ex);
            return defaultValue;
        }
    }

    public static OrderStatus parseOrderStatus(String orderValue, OrderStatus defaultValue) {
        try {
            return OrderStatus.valueOf(orderValue.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException ex) {
            LOGGER.error("order by status parse exception: ", ex);
            return defaultValue;
        }
    }

    public static OrderStatus parseOrderStatus(String orderValue) {
        try {
            return OrderStatus.valueOf(orderValue.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException ex) {
            LOGGER.error("order by status parse exception: ", ex);
            throw new OrderStatusParseException("Cannot parse order status: ", ex);
        }
    }

    public static VacationDateDTO parseVacationDateFromRequest(HttpServletRequest request) {
        LocalDate startsAt = parseDate(request.getParameter("startsAt"),
                LocalDate.now().minusDays(DEFAULT_DAYS_OFFSET));
        LocalDate endsAt = parseDate(request.getParameter("endsAt"),
                LocalDate.now().plusDays(DEFAULT_DAYS_OFFSET));

        LOGGER.info("{}", startsAt);
        LOGGER.info("{}", endsAt);
        return new VacationDateDTO(startsAt, endsAt);
    }

    public static Pageable parsePageableFromRequest(HttpServletRequest request) {
        Integer page = parseInteger(request.getParameter("page"), DEFAULT_PAGE_NUMBER);
        Integer size = parseInteger(request.getParameter("size"), DEFAULT_PAGE_SIZE);
        SortType sortBy = parseSortType(request.getParameter("sort"),
                SortType.ID);

        if (size < MIN_PAGE_SIZE || size > MAX_PAGE_SIZE) {
            size = DEFAULT_PAGE_SIZE;
        }

        return new Pageable(page, size, sortBy);
    }
}