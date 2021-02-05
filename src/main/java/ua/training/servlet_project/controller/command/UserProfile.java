package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.PageDTO;
import ua.training.servlet_project.controller.dto.UserOrderDTO;
import ua.training.servlet_project.controller.dto.UserProfileDTO;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.entity.User;
import ua.training.servlet_project.model.exceptions.ForbiddenPageException;
import ua.training.servlet_project.model.exceptions.UserNotFoundException;
import ua.training.servlet_project.model.service.OrderService;
import ua.training.servlet_project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static ua.training.servlet_project.model.util.RequestParamsParser.parseLong;
import static ua.training.servlet_project.model.util.RequestParamsParser.parsePageableFromRequest;

public class UserProfile implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UserProfile.class);
    public static final Pattern ID_PATH_VARIABLE_PATTERN = Pattern.compile("^/app/users/(\\d+).*(?!a-zA-Z|/)$");
    private static final String USER_PROFILE_PAGE = "/JSP/user.jsp";
    private static final String FORBIDDEN_PAGE_EXCEPTION_MESSAGE = "Cannot access this page";
    private static final String USER_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE = "Cannot find user by id: ";
    private final UserService userService;
    private final OrderService orderService;

    public UserProfile() {
        this.userService = new UserService();
        this.orderService = new OrderService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = request.getRequestURI();
        String userProfileIdValue = ID_PATH_VARIABLE_PATTERN.matcher(path).replaceAll("$1");
        Long userProfileId = parseLong(userProfileIdValue,
                new UserNotFoundException(USER_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE + userProfileIdValue));
        User authenticatedUser = (User) request.getSession().getAttribute("user");
        Pageable pageable = parsePageableFromRequest(request);

        if (!authenticatedUser.getId().equals(userProfileId)) {
            throw new ForbiddenPageException(FORBIDDEN_PAGE_EXCEPTION_MESSAGE);
        }
        UserProfileDTO userProfileDTO = userService.getUserById(userProfileId);
        Page<UserOrderDTO> orderPage = orderService.getAllUserOrders(pageable, userProfileId);

        request.setAttribute("userProfile", userProfileDTO);
        request.setAttribute("orders", orderPage.getContent());
        request.setAttribute("page", getOrdersPageDTO(orderPage, orderPage.getPageable(), userProfileId));

        return USER_PROFILE_PAGE;
    }

    private PageDTO getOrdersPageDTO(Page<UserOrderDTO> orderPage, Pageable currentPageable, Long userId) {
        LOGGER.info(orderPage.getTotalPages());
        return PageDTO.builder()
                .limit(currentPageable.getPageSize())
                .prevPage(currentPageable.getPageNumber() - 1)
                .nextPage(currentPageable.getPageNumber() + 1)
                .currentPage(currentPageable.getPageNumber() + 1)
                .totalPages(orderPage.getTotalPages())
                .hasPrev(orderPage.hasPrevious())
                .hasNext(orderPage.hasNext())
                .url("/app/users/" + userId)
                .build();
    }
}
