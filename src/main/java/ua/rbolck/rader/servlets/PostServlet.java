package ua.rbolck.rader.servlets;

import org.apache.log4j.Logger;
import ua.rbolck.rader.dao.PostDAOI;
import ua.rbolck.rader.dao.PostDAOImpl;
import ua.rbolck.rader.entity.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(PostServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDAOI postDAO = new PostDAOImpl();
        int id = 1;
        try {
            id = Integer.parseInt(req.getParameter("id"));
            Post post = postDAO.get(id);
            if (post != null) {
                req.setAttribute("post", post);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/post.jsp");
                requestDispatcher.forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            log.error("NumberFormatException present while get parameter id = " + req.getParameter("id"), e);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
