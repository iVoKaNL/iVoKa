package nl.ivoka.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebTestServlet extends HttpServlet {

    private boolean debug = false, listing = false, publicVar = false;

    @Override
    public void init() {
        ServletConfig config = getServletConfig();
        ServletContext context = getServletContext();

        String strDebug = config.getInitParameter("debug");
        if (strDebug.equals("true")) debug = true;
        String strListing = config.getInitParameter("listing");
        if (strListing.equals("true")) listing = true;
        String strPublicVar = context.getInitParameter("publicVar");
        if (strPublicVar.equals("true")) publicVar = true;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Set the response message's MIME type
        response.setContentType("text/html;charset=UTF-8");
        // Allocate a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();

        out.println("hello world");
        out.flush();
        out.close();
    }
}
