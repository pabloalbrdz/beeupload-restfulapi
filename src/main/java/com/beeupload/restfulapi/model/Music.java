package com.beeupload.restfulapi.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "musics")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long musicid;

    @Column
    private String name;

    @Column
    private String artist;

    @Column
    private String path;

    @JoinTable(
            name = "rel_musics_users",
            joinColumns = @JoinColumn(name = "musicid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "userid", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users;

}
