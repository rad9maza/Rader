package ua.rbolck.rader.dao;

import ua.rbolck.rader.entity.User;

import java.util.Collection;

public interface UserDAOI {
    User get(int id);

    Collection<User> getAll();

    Collection<User> getAllLimited(int limit);

    boolean save(User post);

    boolean remove(int id);

}
