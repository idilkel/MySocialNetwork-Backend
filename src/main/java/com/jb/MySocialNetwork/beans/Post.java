package com.jb.MySocialNetwork.beans;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

//    @ManyToMany(mappedBy = "likeUsersList")
////    @ToString.Exclude
//    @JsonIgnore
//    private List<User> likeUsersList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_like",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    @JsonManagedReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ToString.Exclude
    private List<User> likeUsersList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "post")
    @ToString.Exclude
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();
}
