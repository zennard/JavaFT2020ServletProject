package ua.training.servlet_project.controller.command;

import ua.training.servlet_project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class LogOut implements Command {
    private static final String REDIRECT_INDEX_PAGE = "/JSP/index.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        removeUserFromContext(request, user.getEmail());
        session.removeAttribute("user");

        return REDIRECT_INDEX_PAGE;
    }

    private static void removeUserFromContext(HttpServletRequest request, String email) {
        Set<String> loggedUsers = (Set<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        loggedUsers.remove(email);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
    }
}
