package ua.rbolck.rader.dao;

import org.apache.log4j.Logger;
import ua.rbolck.rader.entity.Post;
import ua.rbolck.rader.model.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.Collection;

public class PostDAOImpl implements PostDAOI {

    private static final Logger log = Logger.getLogger(PostDAOImpl.class);

    private static final String GET_POST_BY_ID_QUERY = "SELECT * FROM posts WHERE post_id = ?";
    private static final String GET_ALL_POSTS = "SELECT * FROM posts WHERE post_parent_id IS NULL";
    private static final String GET_LAST_N_POSTS = "SELECT * FROM posts WHERE post_parent_id IS NULL ORDER BY creation_date DESC LIMIT ?";

    private static final String INSERT_POST = "INSERT INTO posts VALUES(DEFAULT, NULL, ?, ?, DEFAULT, DEFAULT, ?, DEFAULT)";
    private static final String DELETE_POST_BY_ID = "DELETE FROM posts WHERE post_id = ?";

    public Post get(int id) {
        Post post = null;
        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_POST_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    post = new Post(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),
                            resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7),
                            resultSet.getTimestamp(8));

                    log.info("Get " + post.toString());
                }
            }
        } catch (SQLException e) {
            log.error("SQLException present while getting post by id=" + id, e);
        } catch (IOException e) {
            log.error("IOException present while getting post by id=" + id, e);
        }
        return post;
    }

    public Collection<Post> getAll() {
        Collection<Post> posts = null;
        Post post;

        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(GET_ALL_POSTS)) {
                log.info("Get posts:");
                while (resultSet.next()) {
                    post = new Post(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),
                            resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7),
                            resultSet.getTimestamp(8));
                    log.info(post.toString());
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException present while getting all posts", e);
        } catch (IOException e) {
            log.error("IOException present while getting all post", e);
        }
        return posts;
    }

    public Collection<Post> getAllLimited(int limit) {
        Collection<Post> posts = null;
        Post post;

        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_LAST_N_POSTS)) {
            ps.setInt(1, limit);
            try (ResultSet resultSet = ps.executeQuery()) {
                log.info("Get last " + limit + " posts:");
                while (resultSet.next()) {
                    post = new Post(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),
                            resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7),
                            resultSet.getTimestamp(8));

                    log.info(post.toString());

                    posts.add(post);

                }
            }
        } catch (SQLException e) {
            log.error("SQLException present while getting all posts", e);
        } catch (IOException e) {
            log.error("IOException present while getting all post", e);
        }
        return posts;
    }

    public boolean save(Post post) {
        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_POST)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setInt(3, post.getAuthor().getId());
            ps.executeQuery();
            return true;
        } catch (SQLException e) {
            log.error("SQLException present while create" + post.toString(), e);
        } catch (IOException e) {
            log.error("IOException present while create" + post.toString(), e);
        }
        return false;
    }

    public boolean remove(int id) {
        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_POST_BY_ID)) {
            ps.setInt(1, id);
            ps.executeQuery();
            log.info("Post with id = " + id + " was deleted");
            return true;
        } catch (SQLException e) {
            log.error("SQLException present while remove post by id=" + id, e);
        } catch (IOException e) {
            log.error("IOException present while remove post by id=" + id, e);
        }
        return false;
    }
}
