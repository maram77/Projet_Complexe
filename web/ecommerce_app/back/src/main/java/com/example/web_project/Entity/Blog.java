package com.example.web_project.Entity;


import jakarta.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Date created_on;
    private Date updated_on;
    private Integer countView;
    private Integer countLike;


    @Lob
    @Column(length = 10485760)
    private byte[] image;


    @ManyToOne
    @JoinColumn(name = "user_id")

    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public Date getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCountView() {
        return countView;
    }

    public void setCountView(Integer countView) {
        this.countView = countView;
    }

    public Integer getCountLike() {
        return countLike;
    }

    public void setCountLike(Integer countLike) {
        this.countLike = countLike;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
