package ua.rbolck.rader.entity;

public class User {

    private int id;
    private String username;
    private String password;
    private int group_id;

    public String toString() {
        return "User with id = " + id + ", has name: " + username;
    }

    public User(int id, int group_id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.group_id = group_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
}
