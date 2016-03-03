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

public class UserServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAOI userDAO = new UserDAOImpl();
        String action = req.getParameter("action");
        String idParam = req.getParameter("id");
        int id = Integer.parseInt((idParam == null || "".equals(idParam)) ? "0" : idParam);
        String groupIdParam = req.getParameter("group_id");
        int groupId = Integer.parseInt((groupIdParam == null || "".equals(groupIdParam)) ? "0" : groupIdParam);
        String jspName = "allUsers.jsp";

        if (!(action == null || "".equals(action))) {
            if ("edit".equals(action) && (id != 0)) {
                User user = userDAO.get(id);
                if (user != null) {
                    req.setAttribute("post", user);
                    jspName = "editUser.jsp";
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else if ("new".equals(action)) {
                jspName = "addUser.jsp";
            }
        } else if (id != 0) {
            User user = userDAO.get(id);
            if (user != null) {
                req.setAttribute("user", user);
                jspName = "user.jsp";
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else if (groupId != 0) {
            System.out.println("groupId= "+groupId);
            Collection<User> users = userDAO.getAllUsersFromGroup(groupId);
            if (users != null) {
                jspName = "usersFromGroup.jsp";
                req.setAttribute("users", users);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            Collection<User> admins = userDAO.getAllUsersFromGroup(2);
            Collection<User> moders = userDAO.getAllUsersFromGroup(3);
            Collection<User> users = userDAO.getAllUsersFromGroup(4);
            req.setAttribute("admins", admins);
            req.setAttribute("moders", moders);
            req.setAttribute("users", users);
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(jspName);
        requestDispatcher.forward(req, resp);
    }


}

