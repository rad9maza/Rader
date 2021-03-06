package ua.rbolck.rader.servlets;

import org.apache.log4j.Logger;
import ua.rbolck.rader.dao.PostDAOI;
import ua.rbolck.rader.dao.PostDAOImpl;
import ua.rbolck.rader.entity.Post;
import ua.rbolck.rader.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

public class PostServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(PostServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDAOI postDAO = new PostDAOImpl();
        String action = req.getParameter("action");
        String idParam = req.getParameter("id");
        int id = Integer.parseInt((idParam == null || "".equals(idParam)) ? "0" : idParam);

        try {
            String jspName = "allPosts.jsp";
            if (!(action == null || "".equals(action))) {
                if ("edit".equals(action) && (id != 0)) {
                    Post post = postDAO.get(id);
                    if (post != null) {
                        req.setAttribute("post", post);
                        jspName = "edit.jsp";
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                } else if ("new".equals(action)) {
                    jspName = "add.jsp";
                }
            } else if (id != 0) {
                Post post = postDAO.get(id);
                if (post != null) {
                    req.setAttribute("post", post);
                    jspName = "post.jsp";
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                Collection<Post> posts = postDAO.getAll();
                req.setAttribute("posts", posts);
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(jspName);
            requestDispatcher.forward(req, resp);
        } catch (NumberFormatException e) {
            log.error("NumberFormatException present while get parameter id = " + id, e);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostDAOI postDAOI = new PostDAOImpl();
        HttpSession session = request.getSession();
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        User author = (User) session.getAttribute("user");
        postDAOI.save(new Post(0, title, content, 0, 0, author, new Timestamp(new Date().getTime())));
        response.sendRedirect("/post");

    }
}
