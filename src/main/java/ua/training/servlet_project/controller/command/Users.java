package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.PageDTO;
import ua.training.servlet_project.controller.dto.UserDTO;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static ua.training.servlet_project.model.util.RequestParamsParser.parsePageableFromRequest;

public class Users implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Users.class);
    private static final String USERS_PAGE = "/JSP/users.jsp";
    private final UserService userService;

    public Users() {
        userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        Pageable pageable = parsePageableFromRequest(request);
        Page<UserDTO> userPage = userService.getAllUsers(pageable);

        request.setAttribute("users", userPage.getContent());
        request.setAttribute("page", getUsersPageDTO(userPage));
        return USERS_PAGE;
    }

    private PageDTO getUsersPageDTO(Page<UserDTO> userPage) {
        Pageable currentPageable = userPage.getPageable();
        return PageDTO.builder()
                .limit(currentPageable.getPageSize())
                .prevPage(currentPageable.getPageNumber() - 1)
                .nextPage(currentPageable.getPageNumber() + 1)
                .currentPage(currentPageable.getPageNumber() + 1)
                .totalPages(userPage.getTotalPages())
                .hasPrev(userPage.hasPrevious())
                .hasNext(userPage.hasNext())
                .url("/app/users")
                .build();
    }
}
