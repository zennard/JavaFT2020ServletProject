package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.OrderDTO;
import ua.training.servlet_project.controller.dto.PageDTO;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

import static ua.training.servlet_project.model.util.RequestParamsParser.parsePageableFromRequest;

public class Orders implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Orders.class);
    private final OrderService orderService;
    public static final String ORDERS_PAGE_REDIRECT = "/JSP/orders.jsp";

    public Orders() {
        orderService = new OrderService();
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

        //do post
        return ORDERS_PAGE_REDIRECT;
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
}
