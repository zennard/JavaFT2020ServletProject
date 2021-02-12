package ua.training.servlet_project.controller.command;

import javax.servlet.http.HttpServletRequest;

public class Landing implements Command {
    private static final String LANDING_PAGE = "/JSP/index.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        return LANDING_PAGE;
    }
}
