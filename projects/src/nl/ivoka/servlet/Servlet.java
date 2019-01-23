package nl.ivoka.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet (
        name = "iVoKaServlet",
        urlPatterns = {"/hello", "/doei", "/test", "/ivoka/test"},
        initParams = {
                @WebInitParam(name = "param1", value = "value1"),
                @WebInitParam(name = "param2", value = "value2")
        },
        asyncSupported = false,
        description = "This is a example servlet"
)
// @WebServlet("/sayHi")

public class Servlet extends HttpServlet {

    String webDirectoryLocation = "src/web/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {

            if (false) { // Main example
                // Set the response message's MIME type
                response.setContentType("text/html;charset=UTF-8");
                // Allocate a output writer to write the response message into the network socket
                PrintWriter outputPrinter = response.getWriter();

                // Write the repsonse message, in an HTML page
                try {
                    outputPrinter.println("<!DOCTYPE html>");
                    outputPrinter.println("<html><head>");
                    outputPrinter.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                    outputPrinter.println("<title>Hello, World</title></head>");
                    outputPrinter.println("<body>");
                    outputPrinter.println("<h1>Hello, world!</h1>");  // says Hello
                    // Echo client's request information
                    outputPrinter.println("<p>Request URI: " + request.getRequestURI() + "</p>");
                    outputPrinter.println("<p>Servlet Path: " + request.getServletPath() + "</p>");
                    outputPrinter.println("<p>Auth Type: " + request.getAuthType() + "</p>");
                    outputPrinter.println("<p>Method: " + request.getMethod() + "</p>");
                    outputPrinter.println("<p>Protocol: " + request.getProtocol() + "</p>");
                    outputPrinter.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
                    outputPrinter.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
                    // Generate a random number upon each request
                    outputPrinter.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
                    outputPrinter.println("</body>");
                    outputPrinter.println("</html>");
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    outputPrinter.close();
                }
            }

            if (false) { // Own example
                ServletOutputStream outputStream = response.getOutputStream();

                if (request.getServletPath().equals("/hello")) {
                    outputStream.write("hello world".getBytes());
                } else if (request.getServletPath().equals("/doei")) {
                    outputStream.write("doei".getBytes());
                } else if (request.getServletPath().equals("/test")) {
                    outputStream.write("test".getBytes());
                } else if (request.getServletPath().equals("/ivoka/test")) {
                    outputStream.write(("ivoka".getBytes()));
                }

                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Redirect POST request to GET request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            doGet(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
