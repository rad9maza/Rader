package ua.rbolck.rader.servlets;

import org.apache.log4j.Logger;
import ua.rbolck.rader.dao.UserDAOI;
import ua.rbolck.rader.dao.UserDAOImpl;
import ua.rbolck.rader.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(LoginServlet.class);

    private static final long serialVersionUID = 1L;
    // get request parameters for userID and password
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAOI userDAOI = new UserDAOImpl();
        String userParam = request.getParameter("user");
        String passwordParam = request.getParameter("pwd");
        User user = userDAOI.getByCredentials(userParam, passwordParam);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30 * 60);
            response.sendRedirect("/index.jsp");
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(request, response);
        }
    }
}