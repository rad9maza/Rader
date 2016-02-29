package ua.rbolck.rader.servlets;

import org.apache.log4j.Logger;
import ua.rbolck.rader.dao.UserDAOI;
import ua.rbolck.rader.dao.UserDAOImpl;
import ua.rbolck.rader.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by robo0914 on 29.02.2016.
 */
public class UserServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAOI userDAO = new UserDAOImpl();
        Collection<User> users = userDAO.getAll();
        req.setAttribute("posts", users);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("allUsers.jsp");
        requestDispatcher.forward(req, resp);


    }
}
