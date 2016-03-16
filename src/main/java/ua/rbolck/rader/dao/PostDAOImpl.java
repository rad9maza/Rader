package ua.rbolck.rader.dao;

import org.apache.log4j.Logger;
import ua.rbolck.rader.entity.Post;
import ua.rbolck.rader.model.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static ua.rbolck.rader.model.DatabaseConnection.sanitize;

public class PostDAOImpl implements PostDAOI {

    private static final Logger log = Logger.getLogger(PostDAOImpl.class);

    private static final String GET_POST_BY_ID_QUERY = "SELECT * FROM posts WHERE post_id = ?";
    private static final String GET_ALL_POSTS = "SELECT * FROM posts WHERE post_parent_id IS NULL ORDER BY creation_date DESC";
    private static final String GET_N_POSTS_FROM = "SELECT * FROM posts WHERE post_parent_id IS NULL ORDER BY creation_date DESC LIMIT ? OFFSET ?";

    private static final String INSERT_POST = "INSERT INTO posts VALUES(DEFAULT, NULL, ?, ?, DEFAULT, DEFAULT, ?, DEFAULT)";
    private static final String DELETE_POST_BY_ID = "DELETE FROM posts WHERE post_id = ?";
    private static final String UPDATE_POST = "UPDATE posts SET post_title = ?, post_content = ?, " +
            "post_likes = ?, post_dislikes = ? , creation_date = ? WHERE post_id = ?";
    private static final String LIKE_POST = "UPDATE posts SET post_likes = post_likes + ? WHERE post_id = ?";
    private static final String DISLIKE_POST = "UPDATE posts SET post_dislikes = post_dislikes + ? WHERE post_id = ?";
    private static final String GET_ALL_COMMENTS_FROM_POST = "SELECT * FROM posts WHERE post_parent_id = ? ORDER BY creation_date ASC";
    private static final String INSERT_COMMENT = "INSERT INTO posts VALUES(DEFAULT, ?, ?, ?, DEFAULT, DEFAULT, ?, DEFAULT)";

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
        Collection<Post> posts = new ArrayList<>();
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

    public Collection<Post> getAllLimited(int limit, int from) {
        Collection<Post> posts = new ArrayList<>();
        Post post;

        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_N_POSTS_FROM)) {
            ps.setInt(1, limit);
            ps.setInt(2, from);
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
             PreparedStatement ps = connection.prepareStatement(post.getId() == 0 ? INSERT_POST : UPDATE_POST)) {
            if (post.getId() == 0) {

                ps.setString(1, sanitize(post.getTitle()));
                ps.setString(2, sanitize(post.getContent()));
                ps.setInt(3, post.getAuthor().getId());
                log.info("New post was added to DB");

            } else {

                ps.setString(1, sanitize(post.getTitle()));
                ps.setString(2, sanitize(post.getContent()));
                ps.setInt(3, post.getLikes());
                ps.setInt(4, post.getDislikes());
                ps.setTimestamp(5, post.getCreationDate());
                ps.setInt(6, post.getId());
                log.info("Post with id = " + post.getId() + "was updated");

            }
            ps.executeUpdate();
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
            ps.executeUpdate();
            log.info("Post with id = " + id + " was deleted");
            return true;
        } catch (SQLException e) {
            log.error("SQLException present while remove post by id=" + id, e);
        } catch (IOException e) {
            log.error("IOException present while remove post by id=" + id, e);
        }
        return false;
    }

    public boolean changeRating(int id, int delta) {
        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement((delta > 0) ? LIKE_POST : DISLIKE_POST)) {
            ps.setInt(1, Math.abs(delta));
            ps.setInt(2, id);
            ps.executeUpdate();
            log.info("Update rating to post with id = " + id + " (" + delta + ")");
            return true;
        } catch (SQLException e) {
            log.error("SQLException present while changing rating of post " + id, e);
        } catch (IOException e) {
            log.error("IOException present while changing rating of post " + id, e);
        }
        return false;
    }

    @Override
    public Collection<Post> getComments(int id) {
        Collection<Post> posts = new ArrayList<>();
        Post post;

        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_ALL_COMMENTS_FROM_POST)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                log.info("Get all comments from post with id = " + id + ":");
                while (resultSet.next()) {
                    post = new Post(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),
                            resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7),
                            resultSet.getTimestamp(8));
                    log.info(post.toString());
                    posts.add(post);

                }
            }
        } catch (SQLException e) {
            log.error("SQLException present while getting all comments from post with id = " + id, e);
        } catch (IOException e) {
            log.error("IOException present while getting all comments from post with id = " + id, e);
        }
        return posts;
    }

    @Override
    public boolean addComment(Post post) {
        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_COMMENT)) {
            ps.setInt(1, post.getParentID());
            ps.setString(2, sanitize(post.getContent()));
            ps.setString(3, sanitize(post.getContent()));
            ps.setInt(4, post.getAuthor().getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.error("SQLException present while create" + post.toString(), e);
        } catch (IOException e) {
            log.error("IOException present while create" + post.toString(), e);
        }
        return false;
    }
}
