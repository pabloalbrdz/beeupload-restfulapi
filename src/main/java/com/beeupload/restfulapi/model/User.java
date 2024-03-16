package com.beeupload.restfulapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userid;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToMany(mappedBy = "users")
    private List<Image> images;

    @ManyToMany(mappedBy = "users")
    private List<Video> videos;

    public User(long id, String username) {
        this.userid = id;
        this.username = username;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
