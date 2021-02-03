package ua.training.servlet_project.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.entity.RoleType;
import ua.training.servlet_project.model.entity.Rule;
import ua.training.servlet_project.model.entity.User;
import ua.training.servlet_project.model.service.RuleService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AccessFilter.class);
    private Map<String, List<Rule>> accessControlList;
    private Map<RoleType, String> roleRedirect;

    public static final String LOGIN_PAGE_PATH = "/app/login";
    public static final String ORDERS_PAGE_PATH = "/app/orders";
    public static final String APARTMENTS_PAGE_PATH = "/app/apartments";
    private static final Pattern RESOURCES_PATTERN = Pattern.compile("^.*/(css|js)/.*$", Pattern.CASE_INSENSITIVE);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        RuleService ruleService = new RuleService();
        accessControlList = ruleService.getAllRules();
        roleRedirect = new EnumMap<>(RoleType.class);
        roleRedirect.put(RoleType.ROLE_UNKNOWN, LOGIN_PAGE_PATH);
        roleRedirect.put(RoleType.ROLE_USER, APARTMENTS_PAGE_PATH);
        roleRedirect.put(RoleType.ROLE_MANAGER, ORDERS_PAGE_PATH);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        User curUser = (User) session.getAttribute("user");
        RoleType role = curUser != null ? curUser.getRole() : RoleType.ROLE_UNKNOWN;
        String path = request.getRequestURI();
        HttpMethodType method = HttpMethodType.valueOf(request.getMethod());

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        LOGGER.info(path);
        LOGGER.info(method);

        if (isAccessAllowed(path, role, method)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String redirectUrl = roleRedirect.getOrDefault(role, APARTMENTS_PAGE_PATH);
            LOGGER.info("redirecting to: {}", redirectUrl);
            ((HttpServletResponse) servletResponse).sendRedirect(redirectUrl);
        }
    }

    private boolean isResourcesPath(String path) {
        Matcher resourceMatcher = RESOURCES_PATTERN.matcher(path);
        return resourceMatcher.matches();
    }

    private boolean isAccessAllowed(String path, RoleType role, HttpMethodType method) {
        return accessControlList.entrySet().stream()
                .anyMatch(entry ->
                        Pattern.compile(entry.getKey()).matcher(path).matches()
                                && entry.getValue().contains
                                (
                                        Rule.builder()
                                                .role(role)
                                                .method(method)
                                                .build()
                                )
                )
                || isResourcesPath(path);
    }

    @Override
    public void destroy() {

    }
}
