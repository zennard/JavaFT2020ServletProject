package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.OrderCreationDTO;
import ua.training.servlet_project.controller.dto.OrderDTO;
import ua.training.servlet_project.controller.dto.OrderItemDTO;
import ua.training.servlet_project.controller.dto.PageDTO;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.entity.User;
import ua.training.servlet_project.model.exceptions.ApartmentNotFoundException;
import ua.training.servlet_project.model.service.ApartmentService;
import ua.training.servlet_project.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static ua.training.servlet_project.model.util.RequestParamsParser.*;

public class Orders implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Orders.class);
    private static final String ORDERS_PAGE_REDIRECT = "/JSP/orders.jsp";
    private static final String APARTMENT_NOT_FOUND_EXCEPTION_MESSAGE = "Cannot find apartment by given id";
    private static final int DEFAULT_DAYS_OFFSET = 3;
    private final OrderService orderService;
    private final ApartmentService apartmentService;

    public Orders() {
        orderService = new OrderService();
        apartmentService = new ApartmentService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            Pageable pageable = parsePageableFromRequest(request);
            Page<OrderDTO> orderPage = orderService.getAllNewOrders(pageable);
            Pageable currentPageable = orderPage.getPageable();

            request.setAttribute("orders", orderPage.getContent());
            request.setAttribute("page", getPageDTO(orderPage, currentPageable));

            return ORDERS_PAGE_REDIRECT;
        }

        return processPostRequest(request);
    }

    private PageDTO getPageDTO(Page<OrderDTO> orderPage, Pageable currentPageable) {
        return PageDTO.builder()
                .limit(currentPageable.getPageSize())
                .prevPage(currentPageable.getPageNumber() - 1)
                .nextPage(currentPageable.getPageNumber() + 1)
                .currentPage(currentPageable.getPageNumber() + 1)
                .totalPages(orderPage.getTotalPages())
                .hasPrev(orderPage.hasPrevious())
                .hasNext(orderPage.hasNext())
                .url("/app/orders")
                .build();
    }

    private String processPostRequest(HttpServletRequest request) {
        LocalDateTime startsAt = parseLocalDateTime(request.getParameter("startsAt"),
                LocalDateTime.now().minusDays(DEFAULT_DAYS_OFFSET));
        LocalDateTime endsAt = parseLocalDateTime(request.getParameter("endsAt"),
                LocalDateTime.now().plusDays(DEFAULT_DAYS_OFFSET));
        List<Long> apartmentIds = parseListOfLong(request.getParameterValues("apartmentIds"),
                new ApartmentNotFoundException(APARTMENT_NOT_FOUND_EXCEPTION_MESSAGE));
        User user = (User) request.getSession().getAttribute("user");

        LOGGER.info("apartment ids:{}", apartmentIds);
        List<OrderItemDTO> items = apartmentService.getAllApartmentsByIds(apartmentIds);
        LOGGER.info("items: {}", items);

        OrderCreationDTO orderDTO = OrderCreationDTO
                .builder()
                .userEmail(user.getEmail())
                .orderDate(LocalDateTime.now())
                .startsAt(startsAt)
                .endsAt(endsAt)
                .orderItems(items)
                .build();

        LOGGER.info("{}", orderDTO);

        orderService.createNewOrder(orderDTO);

        return String.format("redirect:/app/apartments?startsAt=%s&endsAt=%s",
                startsAt.toLocalDate(),
                endsAt.toLocalDate());
    }
}
