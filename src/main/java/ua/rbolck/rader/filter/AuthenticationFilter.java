package ua.rbolck.rader.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthenticationFilter.class);


    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        log.info("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        log.info("Requested Resource::" + uri);
        HttpSession session = req.getSession(false);
        if (session == null && !(uri.endsWith("html") || uri.endsWith("LoginServlet"))) {
            log.info("Unauthorized access request");
            res.sendRedirect("/login.html");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        //close any resources here
    }
}