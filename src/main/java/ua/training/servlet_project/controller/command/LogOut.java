package ua.training.servlet_project.controller.command;

import ua.training.servlet_project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class LogOut implements Command {
    private static final String REDIRECT_LOGIN_PAGE = "redirect:/login?logout";

    @Override
    public String execute(HttpServletRequest request) {
        removeUserFromContext(request);

        return REDIRECT_LOGIN_PAGE;
    }

    private static void removeUserFromContext(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();

        Set<String> loggedUsers = (Set<String>) session.getServletContext()
                .getAttribute("loggedUsers");
        loggedUsers.remove(email);
        session.getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        session.removeAttribute("user");
    }
}
