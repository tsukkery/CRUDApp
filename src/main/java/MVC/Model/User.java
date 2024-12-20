package MVC.Model;

import java.util.Date;

public class User {
    private int ind;
    private String name;
    private String id;
    private Date createdAt;
    private Date updatedAt;

    public User(String name) {
        this.name = name;
    }

    public User(int ind, String id, String name, Date createdAt) {
        this.ind = ind;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public User(int ind, String id, String name, Date createdAt, Date updatedAt) {
        this.ind = ind;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getInd() {
        return ind;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
