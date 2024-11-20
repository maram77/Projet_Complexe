package com.example.web_project.Entity;



import jakarta.persistence.*;
import java.time.Instant;
import java.util.Date;


@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Date created_on;



    @ManyToOne
    @JoinColumn(name = "user_id")

    private User user;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)

    private Blog blog;

    // Constructors, getters, and setters

    public Comment() {
    }

    public Comment(String content, User user, Blog blog) {
        this.content = content;
        this.user = user;
        this.blog = blog;
    }



    public Long getId() {
        return id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }
}
