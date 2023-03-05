package com.jb.MySocialNetwork.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String story;

    private String picture;

    private LocalDateTime time = LocalDateTime.now();

    @ManyToOne
    @ToString.Exclude//Just too detailed print
    private User user;

    private int likes = 0;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "post")
//    @ToString.Exclude
    @JsonIgnore
    private List<Like> likesList = new ArrayList<>();


    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "post")
    @ToString.Exclude
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();
}
