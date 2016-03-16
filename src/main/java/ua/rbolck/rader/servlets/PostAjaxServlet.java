package ua.rbolck.rader.servlets;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ua.rbolck.rader.dao.PostDAOI;
import ua.rbolck.rader.dao.PostDAOImpl;
import ua.rbolck.rader.entity.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class PostAjaxServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(PostAjaxServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject obj = new JSONObject();
        PrintWriter pw = resp.getWriter();
        resp.setContentType("application/json");

        String action = req.getParameter("action");

        String postIdParameter = req.getParameter("id");
        int postId = Integer.parseInt((postIdParameter == null || "".equals(postIdParameter)) ? "0" : postIdParameter);

        String deltaParameter = req.getParameter("delta");
        int delta = Integer.parseInt((deltaParameter == null || "".equals(deltaParameter)) ? "5" : deltaParameter);

        String from = req.getParameter("fromPost");
        int fromPost = Integer.parseInt((from == null || "".equals(from)) ? "5" : from);

        if (action != null && "rating".equals(action) && delta != 0) {
            log.info("Do rating: " + delta + " Post with id = " + postId);

            PostDAOI postDAO = new PostDAOImpl();
            postDAO.changeRating(postId, delta);
            obj.put("success", "true");
        } else if ("getNextFivePosts".equals(action)) {
            log.info("Do getNext " + delta + " posts: ");

            PostDAOI postDAO = new PostDAOImpl();
            Collection<Post> posts = postDAO.getAllLimited(delta, fromPost);

            JSONArray list = new JSONArray();
            for (Post post : posts) {
                log.info("\n" + post.toString());
                JSONObject postJSON = new JSONObject();

                postJSON.put("id", post.getId());
                postJSON.put("title", post.getTitle());
                postJSON.put("content", post.getContent());
                postJSON.put("likes", post.getLikes());
                postJSON.put("dislikes", post.getDislikes());
                postJSON.put("author", post.getAuthorId());

                JSONArray author = new JSONArray();
                JSONObject authorJSON = new JSONObject();

                authorJSON.put("authorID", post.getAuthor().getId());
                authorJSON.put("authorName", post.getAuthor().getUsername());
                author.add(authorJSON);

                postJSON.put("author", author);

                list.add(postJSON);
            }
            obj.put("success", "true");
            obj.put("posts", list);
        } else {
            obj.put("success", "false");
        }
        pw.write(obj.toString());
        pw.close();
    }
}
