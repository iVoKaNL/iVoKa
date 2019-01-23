package nl.ivoka.servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(
        name = "upload",
        urlPatterns = {"/upload"}
)

public class CookieServlet extends HttpServlet {
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
            String title = rb.getString("cookies.title");
            out.println("<head><title>" + title + "</title></head>");
            out.println("<body>");
            out.println("<h3>" + title + "</h3>");

            // Display the cookies returned by the client
            Cookie[] cookies = request.getCookies();
            if ((cookies != null) && (cookies.length > 0)) {
                out.println(rb.getString("cookies.cookies") + "<br />");
                for (Cookie cookie : cookies) {
                    out.println("Cookie Name: " + htmlFilter(cookie.getName()) + "<br />");
                    out.println("Cookie Value: " + htmlFilter(cookie.getValue()) + "<br />");
                }
            } else {
                out.println(rb.getString("cookies.no-cookies") + "<br />");
            }
            out.println("<br />");

            // Create a new cookie if cookiename and cookievalue present in the request
            String cookieName = request.getParameter("cookiename");
            if (cookieName != null) cookieName = cookieName.trim();
            String cookieValue = request.getParameter("cookievalue");
            if (cookieValue != null) cookieValue = cookieValue.trim();
            if (cookieName != null && !cookieName.equals("")
                    && cookieValue != null && !cookieValue.equals("")) {
                Cookie cookie = new Cookie(cookieName, cookieValue);
                response.addCookie(cookie);
                out.println(rb.getString("cookies.set") + "<br />");
                out.print(rb.getString("cookies.name") + "  "
                        + htmlFilter(cookieName) + "<br />");
                out.print(rb.getString("cookies.value") + "  "
                        + htmlFilter(cookieValue));
            }
            out.println("<br /><br />");

            // Display a form to prompt the user to create a new cookie
            out.println(rb.getString("cookies.make-cookie") + "<br />");
            out.print("<form method='get'>");
            out.print(rb.getString("cookies.name"));
            out.println("<input type='text' name='cookiename'><br />");
            out.print(rb.getString("cookies.value"));
            out.println("<input type='text' name='cookievalue'><br />");
            out.println("<input type='submit' value='SEND'>");
            out.println("</form></body></html>");
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