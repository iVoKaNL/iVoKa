package nl.ivoka.servlet;

import java.io.*;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebFilter(urlPatterns={"/*"})
public class RequestFilterServlet implements Filter {
    private static final Logger logger
            = Logger.getLogger(RequestFilterServlet.class.getName());

    @Override
    public void init(FilterConfig config) throws ServletException {
        logger.info("RequestTimerFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        long before = System.currentTimeMillis();
        chain.doFilter(request, response);
        long after = System.currentTimeMillis();
        String path = ((HttpServletRequest)request).getRequestURI();
        logger.info(path + ": " + (after - before) + " msec");
    }

    @Override
    public void destroy() {
        logger.info("RequestTimerFilter destroyed");
    }
}