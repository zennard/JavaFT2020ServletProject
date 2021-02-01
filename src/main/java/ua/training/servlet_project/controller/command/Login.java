package ua.training.servlet_project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.entity.User;
import ua.training.servlet_project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class Login implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Login.class);

    private static final String LOGIN_PAGE = "/JSP/login.jsp";
    private static final String INDEX_PAGE_REDIRECT = "redirect:/";
    private static final String ERROR_PAGE_REDIRECT = "redirect:/WEB-INF/error.jsp";
    private final UserService userService;

    public Login() {
        userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return LOGIN_PAGE;
        }
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("pass");

        LOGGER.info(email + " " + password);

        User user = userService.getUserByEmailAndPassword(email, password)
                .orElse(null);
        if (user != null) {
            if (isUserAlreadyLogged(request, email)) {
                return ERROR_PAGE_REDIRECT;
            }
            addUserToContext(request, email);
            session.setAttribute("user", user);
        } else {
            request.setAttribute("error", "credentials");
            return LOGIN_PAGE;
        }

        LOGGER.info("user:");
        LOGGER.info(user);

        return INDEX_PAGE_REDIRECT;
    }

    private static boolean isUserAlreadyLogged(HttpServletRequest request, String email) {
        Set<String> loggedUsers = (Set<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        if (loggedUsers == null) {
            request.getSession().getServletContext().setAttribute("loggedUsers", new HashSet<>());
            return false;
        }
        return loggedUsers.stream().anyMatch(email::equals);
    }

    private static void addUserToContext(HttpServletRequest request, String email) {
        Set<String> loggedUsers = (Set<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        loggedUsers.add(email);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
    }
}
