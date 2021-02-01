package ua.training.servlet_project.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionLocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter("lang") != null) {
            req.getSession().setAttribute("lang", req.getParameter("lang"));
        } else {
            req.getSession().setAttribute("lang", "en");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
