package ua.training.servlet_project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.command.Exception;
import ua.training.servlet_project.controller.command.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Servlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Servlet.class);
    public static final String APARTMENTS_PAGE_REDIRECT = "redirect:/apartments";
    private static final String ROOT_PATTERN = ".*/app/";
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init() {
        commands.put("landing", new Landing());
        commands.put("logout", new LogOut());
        commands.put("login", new Login());
        commands.put("register", new Registration());
        commands.put("exception", new Exception());
        commands.put("apartments", new Apartments());
        commands.put("apartments/\\d+", new ApartmentProfile());
        commands.put("orders", new Orders());
        commands.put("orders/update/\\d+", new UpdateOrder());
        commands.put("users/\\d+", new UserProfile());
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI().replaceAll(ROOT_PATTERN, "");
        LOGGER.info(path);

        Command command = commands.entrySet().stream()
                .filter(entry -> Pattern.compile(entry.getKey()).matcher(path).matches())
                .map(Map.Entry::getValue)
                .findAny()
                .orElse(req -> APARTMENTS_PAGE_REDIRECT);

        String page = command.execute(request);
        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", "/app"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}