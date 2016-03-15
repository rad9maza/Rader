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
import java.util.Calendar;
import java.util.Collection;

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
                Collection<Post> posts = postDAO.getAllLimited(10, 0);
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
        User author = (User) session.getAttribute("user");

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        int id = Integer.parseInt((idParam == null ? "0" : ("".equals(idParam)) ? "-1" : idParam));

        if (!(action == null || "".equals(action)) && id!=-1) {
            if ("edit".equals(action) || "new".equals(action)) {
                postDAOI.save(new Post(id, title, content, 0, 0, author, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())));
                response.sendRedirect("/post");
            } else if ("remove".equals(action)) {
                postDAOI.remove(id);
                response.sendRedirect("/post");
            } else if ("newComment".equals(action)) {
                postDAOI.addComment(new Post(0, id, title, content, 0, 0, author, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())));
                response.sendRedirect("/post?id=" + id);
            } else if ("removeComment".equals(action)) {
                postDAOI.remove(id);
                response.sendRedirect("/post?id=" + id);
            }
        }



    }
}
