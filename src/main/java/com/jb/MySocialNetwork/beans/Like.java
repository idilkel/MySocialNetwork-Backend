package com.jb.MySocialNetwork.beans;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @ToString.Exclude
    private Post post;

    private long likingUserId;
}
