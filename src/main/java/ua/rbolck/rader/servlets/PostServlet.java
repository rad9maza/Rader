package ua.rbolck.rader.servlets;

import ua.rbolck.rader.dao.PostDAOI;
import ua.rbolck.rader.dao.PostDAOImpl;
import ua.rbolck.rader.entity.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDAOI postDAO = new PostDAOImpl();
        Post post = postDAO.get(1);

        PrintWriter writer = resp.getWriter();

        req.setAttribute("post", post);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/post.jsp");
        requestDispatcher.forward(req, resp);
    }
}
