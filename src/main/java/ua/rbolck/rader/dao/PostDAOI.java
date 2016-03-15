package ua.rbolck.rader.dao;

import ua.rbolck.rader.entity.Post;

import java.util.Collection;

public interface PostDAOI {

    Post get(int id);

    Collection<Post> getAll();

    Collection<Post> getAllLimited(int limit, int from);

    boolean save(Post post);

    boolean remove(int id);

    boolean changeRating(int id, int delta);

    Collection<Post> getComments(int id);

    boolean addComment(Post post);
}
