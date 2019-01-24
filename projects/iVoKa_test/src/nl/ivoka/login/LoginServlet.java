package nl.ivoka.login;

import nl.ivoka.ebookExample.EntryServlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/login"},
        initParams = {
                @WebInitParam(name = "param1", value = "value1"),
                @WebInitParam(name = "param2", value = "value2")
        },
        asyncSupported = false,
        description = "This is the login servlet"
)

public class LoginServlet extends HttpServlet {

    //private String databaseURL, username, password;
    private DataSource pool; // Database connection pool

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            // Create a JNDI Initial context to be able to lookup the DataSource
            InitialContext ctx = new InitialContext();
            // Lookup the DataSource, which will be backed by a pool
            //   that the application server provides.
            pool = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql_ebookshop");
            if (pool == null)
                throw new ServletException("Unknown DataSource 'jdbc/mysql_ebookshop'");
        } catch (NamingException ex) {
            Logger.getLogger(EntryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //// Retrieve the database-URL, username, password from webapp init parameters
        //super.init(config);
        //ServletContext context = config.getServletContext();
        //databaseURL = context.getInitParameter("databaseURL");
        //username = context.getInitParameter("username");
        //password = context.getInitParameter("password");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        Statement stmt = null;
        try {
            out.println("<html><head><title>Login</title></head><body>");
            out.println("<h2>Login</h2>");

            conn = pool.getConnection();  // Get a connection from the pool
            stmt = conn.createStatement();

            // Retrieve and process request parameters: username and password
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            boolean hasUserName = userName != null && ((userName = userName.trim()).length() > 0);
            boolean hasPassword = password != null && ((password = password.trim()).length() > 0);

            // Validate input request parameters
            if (!hasUserName) {
                out.println("<h3>Please Enter Your username!</h3>");
            } else if (!hasPassword) {
                out.println("<h3>Please Enter Your password!</h3>");
            } else {
                // Verify the username/password and retrieve the role(s)
                StringBuilder sqlStr = new StringBuilder();
                sqlStr.append("SELECT role FROM users, user_roles WHERE ");
                sqlStr.append("STRCMP(users.username, '")
                        .append(userName).append("') = 0 ");
                sqlStr.append("AND STRCMP(users.password, PASSWORD('")
                        .append(password).append("')) = 0 ");
                sqlStr.append("AND users.username = user_roles.username");
                //System.out.println(sqlStr);  // for debugging

                ResultSet rset = stmt.executeQuery(sqlStr.toString());

                // Check if username/password are correct
                if (!rset.next()) {  // empty ResultSet
                    out.println("<h3>Wrong username/password!</h3>");
                    out.println("<p><a href='index.html'>Back to Login Menu</a></p>");
                } else {
                    // Retrieve the roles
                    List<String> roles = new ArrayList<>();
                    do {
                        roles.add(rset.getString("role"));
                    } while (rset.next());

                    // Create a new HTTPSession and save the username and roles
                    // First, invalidate the session. if any
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.invalidate();
                    }
                    session = request.getSession(true);
                    synchronized (session) {
                        session.setAttribute("username", userName);
                        session.setAttribute("roles", roles);
                    }

                    out.println("<p>Hello, " + userName + "!</p>");
                    out.println("<p><a href='dosomething'>Do Somethings</a></p>");
                }
            }
            out.println("</body></html>");

        } catch (SQLException ex) {
            out.println("<h3>Service not available. Try again later!</h3></body></html>");
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();  // Return the connection to the pool
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}