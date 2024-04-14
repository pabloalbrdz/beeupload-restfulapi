package com.beeupload.restfulapi.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "documents")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long documentid;

    @Column
    private String name;

    @Column
    private String path;

    @JoinTable(
            name = "rel_documents_users",
            joinColumns = @JoinColumn(name = "documentid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "userid", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users;

}
