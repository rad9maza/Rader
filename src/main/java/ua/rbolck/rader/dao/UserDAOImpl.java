package ua.rbolck.rader.dao;

import org.apache.log4j.Logger;
import ua.rbolck.rader.entity.User;
import ua.rbolck.rader.model.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.Collection;

public class UserDAOImpl implements UserDAOI {

    private static final Logger log = Logger.getLogger(PostDAOImpl.class);

    public static final String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM users";
    public static final String GET_ALL_USERS_FROM_GROUP = "SELECT * FROM users WHERE group_id = ?";

    private static final String INSERT_USER = "INSERT INTO users VALUES(DEFAULT, ?, ?, ?)";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";

    @Override
    public User get(int id) {
        User user = null;
        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_USER_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4));

                    log.info("Get " + user.toString());
                }
            }
        } catch (SQLException e) {
            log.error("SQLException present while getting user by id=" + id, e);
        } catch (IOException e) {
            log.error("IOException present while getting user by id=" + id, e);
        }
        return user;
    }

    @Override
    public Collection<User> getAll() {
        Collection<User> users = null;
        User user;

        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(GET_ALL_USERS)) {
                log.info("Get users:");
                while (resultSet.next()) {
                    user = new User(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4));
                    log.info(user.toString());
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException present while getting all users", e);
        } catch (IOException e) {
            log.error("IOException present while getting all users", e);
        }
        return users;
    }

    @Override
    public Collection<User> getAllUsersFromGroup(int groupID) {
        Collection<User> users = null;
        User user;

        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_ALL_USERS_FROM_GROUP)) {
            ps.setInt(1, groupID);
            try (ResultSet resultSet = ps.executeQuery()) {
                log.info("Get users from group with groupID " + groupID);
                while (resultSet.next()) {
                    user = new User(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4));
                    log.info(user.toString());
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException present while getting all posts", e);
        } catch (IOException e) {
            log.error("IOException present while getting all post", e);
        }
        return users;
    }

    @Override
    public boolean save(User user) {
        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_USER)) {
            ps.setInt(1, user.getGroup_id());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.executeQuery();
            return true;
        } catch (SQLException e) {
            log.error("SQLException present while create" + user.toString(), e);
        } catch (IOException e) {
            log.error("IOException present while create" + user.toString(), e);
        }
        return false;
    }

    @Override
    public boolean remove(int id) {
        try (DatabaseConnection db = DatabaseConnection.getInstance();
             Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER_BY_ID)) {
            ps.setInt(1, id);
            ps.executeQuery();
            log.info("User with id = " + id + " was deleted");
            return true;
        } catch (SQLException e) {
            log.error("SQLException present while remove user by id=" + id, e);
        } catch (IOException e) {
            log.error("IOException present while remove user by id=" + id, e);
        }
        return false;
    }
}
