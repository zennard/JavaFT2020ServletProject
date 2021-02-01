package ua.training.servlet_project.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodingFilter implements Filter {
    private static Pattern excludeUrls = Pattern.compile("^.*/(css|js)/.*$", Pattern.CASE_INSENSITIVE);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String path = ((HttpServletRequest) servletRequest).getRequestURI();
        if (!isExcludedFromFilter(path)) {
            servletResponse.setContentType("text/html");
            servletResponse.setCharacterEncoding("UTF-8");
            servletRequest.setCharacterEncoding("UTF-8");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isExcludedFromFilter(String path) {
        Matcher resourceMatcher = excludeUrls.matcher(path);
        return resourceMatcher.matches();
    }

    @Override
    public void destroy() {
    }
}
