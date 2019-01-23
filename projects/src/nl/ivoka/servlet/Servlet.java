package nl.ivoka.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet (
        name = "iVoKaServlet",
        urlPatterns = {"/hello"}
)

public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();

            outputStream.write("hello world".getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
