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
import java.util.Collection;

public class PostServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(PostServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspName = "allPosts.jsp";
        PostDAOI postDAO = new PostDAOImpl();
        String action = req.getParameter("action");
        int id = 1;
        try {
            id = Integer.parseInt(req.getParameter("id"));

            if ((action == null || "".equals(action) && (id == 0 || "".equals(id)))) {
                Collection<Post> posts = postDAO.getAll();
                req.setAttribute("posts", posts);
                jspName = "allPosts.jsp";
            } else if ("edit".equals(action)) {
                Post post = postDAO.get(id);
                if (post != null) {
                    req.setAttribute("post", post);
                    jspName = "edit.jsp";
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else if ("new".equals(action)) {
                jspName = "add.jsp";
            } else {
                Post post = postDAO.get(id);
                if (post != null) {
                    req.setAttribute("post", post);
                    jspName = "post.jsp";
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }

            RequestDispatcher requestDispatcher = req.getRequestDispatcher(jspName);
            requestDispatcher.forward(req, resp);
        } catch (NumberFormatException e) {
            log.error("NumberFormatException present while get parameter id = " + req.getParameter("id"), e);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }


    }
}
