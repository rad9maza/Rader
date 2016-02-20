package ua.rbolck.rader.entity;

import ua.rbolck.rader.dao.UserDAOI;
import ua.rbolck.rader.dao.UserDAOImpl;

import java.sql.Timestamp;

public class Post {

    private int id;
    private String title;
    private String content;
    private int likes;
    private int dislikes;
    private int authorId;
    private User author;
    private Timestamp creationDate;

    public Post(int id, String title, String content, int likes, int dislikes, User author, Timestamp creationDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.author = author;
        this.creationDate = creationDate;
    }

    public Post(int id, String title, String content, int likes, int dislikes, int userId, Timestamp creationDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.authorId = authorId;
        this.creationDate = creationDate;
    }

    public String toString() {
        return "Post with id = " + id + ", has title: " + title + ", content: " + content + ", and creationDate: " + creationDate.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public User getAuthor() {
        if (this.author == null) {
            UserDAOI userDAO = new UserDAOImpl();
            this.author = userDAO.get(this.authorId);
        }
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
