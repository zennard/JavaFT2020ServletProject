package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.UpdateOrderDTO;
import ua.training.servlet_project.model.entity.OrderStatus;
import ua.training.servlet_project.model.entity.RoleType;
import ua.training.servlet_project.model.entity.User;
import ua.training.servlet_project.model.exceptions.ForbiddenPageException;
import ua.training.servlet_project.model.exceptions.OrderNotFoundException;
import ua.training.servlet_project.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

import static ua.training.servlet_project.model.util.RequestParamsParser.parseLong;
import static ua.training.servlet_project.model.util.RequestParamsParser.parseOrderStatus;

public class UpdateOrder implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UpdateOrder.class);
    private static final Pattern ID_PATH_VARIABLE_PATTERN = Pattern.compile("^/app/orders/update/(\\d+).*(?!a-zA-Z|/)$");
    private static final String ORDER_NOT_FOUND_EXCEPTION_MESSAGE = "Order not found by id";
    private static final String FORBIDDEN_PAGE_EXCEPTION_MESSAGE = "Cannot access this page";
    private static final String APARTMENTS_PAGE_REDIRECT = "redirect:/apartments";
    private final OrderService orderService;

    public UpdateOrder() {
        orderService = new OrderService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = request.getRequestURI();
        HttpSession session = request.getSession();

        User curUser = (User) session.getAttribute("user");
        OrderStatus newStatus = parseOrderStatus(request.getParameter("orderStatus"));
        if (isNotAuthorized(curUser, newStatus)) {
            throw new ForbiddenPageException(FORBIDDEN_PAGE_EXCEPTION_MESSAGE);
        }

        Long id = parseLong(ID_PATH_VARIABLE_PATTERN.matcher(path).replaceAll("$1"),
                new OrderNotFoundException(ORDER_NOT_FOUND_EXCEPTION_MESSAGE));
        orderService.updateOrderStatus(
                UpdateOrderDTO.builder()
                        .id(id)
                        .status(newStatus)
                        .build()
        );
        return APARTMENTS_PAGE_REDIRECT;
    }

    private boolean isNotAuthorized(User user, OrderStatus status) {
        return user != null && status.equals(OrderStatus.APPROVED)
                && !user.getRole().equals(RoleType.ROLE_MANAGER);
    }
}
