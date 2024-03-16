package com.beeupload.restfulapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "videos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long videoid;

    @Column
    private String name;

    @Column
    private String path;

    @JoinTable(
            name = "rel_videos_users",
            joinColumns = @JoinColumn(name = "videoid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "userid", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users;

}
