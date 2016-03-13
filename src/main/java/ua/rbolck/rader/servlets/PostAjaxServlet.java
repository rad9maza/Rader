package ua.rbolck.rader.servlets;

import ua.rbolck.rader.dao.PostDAOI;
import ua.rbolck.rader.dao.PostDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PostAjaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("application/json");
        StringBuilder sb = new StringBuilder("{");
        String action = req.getParameter("action");
        String postIdParameter = req.getParameter("id");
        String deltaParameter = req.getParameter("delta");
        int postId = Integer.parseInt((postIdParameter == null || "".equals(postIdParameter)) ? "0" : postIdParameter);
        int delta = Integer.parseInt((deltaParameter == null || "".equals(deltaParameter)) ? "0" : deltaParameter);
        if (action != null && "rating".equals(action) && postId != 0 && delta != 0) {
            PostDAOI postDAO = new PostDAOImpl();
            postDAO.changeRating(postId, delta);
            sb.append("\"success\":\"true\"");
        } else {
            sb.append("\"success\":\"false\"");
        }
        sb.append("}");
        pw.write(sb.toString());
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
