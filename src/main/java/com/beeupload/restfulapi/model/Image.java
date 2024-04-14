package com.beeupload.restfulapi.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageid;

    @Column
    private String path;

    @JoinTable(
            name = "rel_images_users",
            joinColumns = @JoinColumn(name = "imageid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "userid", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users;

}
