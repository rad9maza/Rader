package ua.rbolck.rader.dao;

import ua.rbolck.rader.entity.User;

import java.util.Collection;

public interface UserDAOI {
    User get(int id);

    Collection<User> getAll();

    Collection<User> getAllUsersFromGroup(int groupID);

    boolean save(User user);

    boolean remove(int id);

    User getByCredentials(String login, String password);
}
