package ua.rbolck.rader.dao;

import org.apache.log4j.Logger;
import ua.rbolck.rader.entity.Post;
import ua.rbolck.rader.model.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class PostDAOImpl implements PostDAOI {

    private static final Logger log = Logger.getLogger(PostDAOImpl.class);

    private static final String GET_POST_BY_ID_QUERY = "SELECT * FROM posts WHERE id = ?";

    //TODO add logging
    public Post get(int id) {
//        DatabaseConnection db = DatabaseConnection.getInstance();
        Post post = null;

        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_POST_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery();) {
                if (resultSet.next()) {

                    //TODO add columns
                    int postId = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    post = new Post(postId, title, "", 0, 0, null, new Date());
                }
            }
        } catch (SQLException e) {
            log.error("Error while getting post by id=" + id, e);
        } catch (IOException e) {
            log.error("Error while getting post by id=" + id, e);
        }
        return post;
    }

    public Collection<Post> getAll() {
        return null;
    }

    public Collection<Post> getAllLimited(int limit) {
        return null;
    }

    public boolean save(Post post) {
        return false;
    }

    public boolean remove(int id) {
        return false;
    }
}
