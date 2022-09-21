package com.jb.MySocialNetwork.beans;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
