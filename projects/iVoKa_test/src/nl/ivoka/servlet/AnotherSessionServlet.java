package nl.ivoka.servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(
        name = "sessionEx",
        urlPatterns = {"/session_example"}
)

public class AnotherSessionServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Set the response message's MIME type
        response.setContentType("text/html;charset=UTF-8");
        // Allocate a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();

        // Use ResourceBundle to keep localized string in "LocalStrings_xx.properties"
        ResourceBundle rb = ResourceBundle.getBundle("LocalStrings",  request.getLocale());

        // Write the response message, in an HTML page
        try {
            out.println("<!DOCTYPE html");  // HTML 5
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            String title = rb.getString("sessions.title");
            out.println("<head><title>" + title + "</title></head>");
            out.println("<body>");
            out.println("<h3>" + title + "</h3>");

            // Return the existing session if there is one. Otherwise, create a new session
            HttpSession session = request.getSession();

            // Display session information
            out.println(rb.getString("sessions.id") + " " + session.getId() + "<br />");
            out.println(rb.getString("sessions.created") + " ");
            out.println(new Date(session.getCreationTime()) + "<br />");
            out.println(rb.getString("sessions.lastaccessed") + " ");
            out.println(new Date(session.getLastAccessedTime()) + "<br /><br />");

            // Set an attribute (name-value pair) if present in the request
            String attName = request.getParameter("attribute_name");
            if (attName != null) attName = attName.trim();
            String attValue = request.getParameter("attribute_value");
            if (attValue != null) attValue = attValue.trim();
            if (attName != null && !attName.equals("")
                    && attValue != null && !attValue.equals("") ) {
                // synchronized session object to prevent concurrent update
                synchronized(session) {
                    session.setAttribute(attName, attValue);
                }
            }

            // Display the attributes (name-value pairs) stored in this session
            out.println(rb.getString("sessions.data") + "<br>");
            Enumeration names = session.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = session.getAttribute(name).toString();
                out.println(htmlFilter(name) + " = "
                        + htmlFilter(value) + "<br>");
            }
            out.println("<br />");

            // Display a form to prompt user to create session attribute
            out.println("<form method='get'>");
            out.println(rb.getString("sessions.dataname"));
            out.println("<input type='text' name='attribute_name'><br />");
            out.println(rb.getString("sessions.datavalue"));
            out.println("<input type='text' name='attribute_value'><br />");
            out.println("<input type='submit' value='SEND'>");
            out.println("</form><br />");

            out.print("<a href='");
            // Encode URL by including the session ID (URL-rewriting)
            out.print(response.encodeURL(request.getRequestURI() + "?attribute_name=foo&attribute_value=bar"));
            out.println("'>Encode URL with session ID (URL re-writing)</a>");
            out.println("</body></html>");
        } finally {
            out.close();  // Always close the output writer
        }
    }

    // Do the same thing for GET and POST requests
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }

    // Filter the string for special HTML characters to prevent
    // command injection attack
    private static String htmlFilter(String message) {
        if (message == null) return null;
        int len = message.length();
        StringBuffer result = new StringBuffer(len + 20);
        char aChar;

        for (int i = 0; i < len; ++i) {
            aChar = message.charAt(i);
            switch (aChar) {
                case '<': result.append("&lt;"); break;
                case '>': result.append("&gt;"); break;
                case '&': result.append("&amp;"); break;
                case '"': result.append("&quot;"); break;
                default: result.append(aChar);
            }
        }
        return (result.toString());
    }
}